package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import apps.robot.dictionary.impl.R
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.DictionaryListViewModel
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.DictionaryHeadersState
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.DictionaryListState
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.SearchWidgetState
import apps.robot.sindarin_dictionary_en.dictionary.navigation.DictionaryInternalFeature
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import java.lang.Math.abs

@Composable
internal fun DictionaryList(
    viewModel: DictionaryListViewModel = getViewModel(),
    navigator: NavHostController
) {
    val state by viewModel.state.collectAsState()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isTopBarVisible = currentDestination?.route != DictionaryFeatureApi.DETAILS_ROUTE

    Scaffold(
        topBar = {
            DictionaryListTopAppBar(
                isTopBarVisible = isTopBarVisible,
                viewModelStoreOwner = viewModelStoreOwner,
                searchWidgetState = state.searchWidgetState,
                onSearchToggle = viewModel::onSearchToggle,
                onTextChange = viewModel::onSearchTextChange,
                searchTextState = state.searchText.collectAsState().value
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
            color = if (state.headersState.shouldShowSelectedHeader.collectAsState().value) {
                colorResource(id = R.color.black).copy(alpha = ContentAlpha.medium)
            } else {
                Color.Transparent
            },
        ) {
            DictionaryListContentRow(
                state = state,
                headersState = state.headersState,
                onDragChange = viewModel::onDragChange,
                onSelectedHeaderIndexChange = viewModel::onSelectedHeaderIndexChange,
                navigator = navigator
            )
        }
    }
}

@Composable
internal fun DictionaryListContentRow(
    state: DictionaryListState,
    headersState: DictionaryHeadersState,
    onDragChange: (Boolean) -> Unit,
    onSelectedHeaderIndexChange: (Int) -> Unit,
    navigator: NavHostController,
    dictionaryInternalFeature: DictionaryInternalFeature = get()
) {
    val words = state.words.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    if (headersState.shouldShowSelectedHeader.collectAsState().value) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 100.dp),
                text = headersState.headers.getOrNull(headersState.selectedHeaderIndex.value)?.asString() ?: "",
                fontSize = 100.sp,
                color = colorResource(id = R.color.white),
            )
        }
    }
    if (words.itemCount == 0 && state.searchWidgetState == SearchWidgetState.OPENED) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = stringResource(id = R.string.dictionary_list_nothing_found),
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(0.9f),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                bottom = 16.dp
            )
        ) {
            items(
                count = words.itemCount,
                key = { index ->
                    words[index]?.id ?: ""
                }) { index ->
                words[index]?.let { word ->
                    WordItem(
                        wordUiModel = word,
                        onClick = {
                            val screen = dictionaryInternalFeature.detailsScreen(
                                it.id,
                                state.dictionaryMode.name
                            )
                            navigator.navigate(screen)
                        }
                    )
                }
            }
        }
        if (headersState.headers.isNotEmpty()) {
            val offsets = remember { mutableStateMapOf<Int, Float>() }
            val scope = rememberCoroutineScope()
            val context = LocalContext.current

            fun updateSelectedIndexIfNeeded(offset: Float): Boolean {
                val index = offsets
                    .mapValues { abs(it.value - offset) }
                    .entries
                    .minByOrNull { it.value }
                    ?.key ?: return false
                onSelectedHeaderIndexChange(index)

                val selectedItemIndex = words.itemSnapshotList.indexOfFirst {
                    it?.word?.asString(context)?.first()?.uppercase() ==
                        headersState.headers.getOrNull(headersState.selectedHeaderIndex.value)?.asString(context)
                }
                scope.launch {
                    if (selectedItemIndex != -1) {
                        listState.scrollToItem(selectedItemIndex)
                    } else if (words.itemCount > 0) {
                        listState.scrollToItem(words.itemCount - 1)
                    }
                }.invokeOnCompletion {
                    if (headersState.isUserDragging.value.not()) {
                        onSelectedHeaderIndexChange(-1)
                    }
                }
                return true
            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .padding(bottom = 16.dp)
                    .weight(0.15f)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            updateSelectedIndexIfNeeded(it.y)
                        }
                    }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { change, _ ->
                                updateSelectedIndexIfNeeded(change.position.y)
                            }, onDragStart = {
                                onDragChange(true)
                            }, onDragEnd = {
                                onSelectedHeaderIndexChange(-1)
                                onDragChange(false)
                            })
                    }
            ) {
                headersState.headers.forEachIndexed { i, header ->
                    val isHeaderSelected = headersState.isUserDragging.value &&
                        i == headersState.selectedHeaderIndex.collectAsState().value
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .onGloballyPositioned {
                                offsets[i] = it.boundsInParent().center.y
                            }
                    ) {
                        Canvas(
                            modifier = Modifier
                                .size(16.dp)
                                .padding(end = 8.dp)
                                .alpha(
                                    if (isHeaderSelected) {
                                        ContentAlpha.high
                                    } else {
                                        0F
                                    }
                                ),
                            onDraw = {
                                drawCircle(color = Color.White)
                            })
                        Text(
                            text = header.asString(),
                            fontSize = 12.sp,
                            color = if (isHeaderSelected) {
                                colorResource(id = R.color.white)
                            } else {
                                MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                            }
                        )
                    }
                }
            }
        }
    }
}