package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
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
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DetailsMode
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.DictionaryListTopAppBar
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.DictionaryListViewModel
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.DictionaryHeadersState
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.DictionaryListState
import apps.robot.sindarin_dictionary_en.dictionary.navigation.DictionaryInternalFeature
import kotlinx.coroutines.launch
import my.nanihadesuka.compose.LazyColumnScrollbar
import my.nanihadesuka.compose.ScrollbarSelectionMode
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import timber.log.Timber
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
                searchWidgetState = state.searchWidgetState,
                onSearchToggle = viewModel::onSearchToggle,
                onTextChange = viewModel::onSearchTextChange,
                searchTextState = state.searchText.collectAsState().value,
                title = stringResource(id = R.string.dictionary_list_toolbar_title),
                hint = stringResource(id = R.string.dictionary_searchbar_hint),
                optionalContent = { DictionaryModeToggle(owner = viewModelStoreOwner) }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
            color = CustomTheme.colors.background
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

@OptIn(ExperimentalFoundationApi::class)
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
    val context = LocalContext.current
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
            color = CustomTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
        )
    }
    if (state.uiState == UiState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                color = CustomTheme.colors.onBackground
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LazyColumnScrollbar(
            listState = listState,
            selectionMode = ScrollbarSelectionMode.Full,
            thumbColor = CustomTheme.colors.primary.copy(alpha = 0.5F),
            thumbSelectedColor = CustomTheme.colors.primary,
            content = {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(0.9f),
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
                                        wordId = it.id,
                                        text = word.word.asString(context),
                                        translation = word.translation.asString(context),
                                        detailsMode = DetailsMode.DICTIONARY.name
                                    )
                                    navigator.navigate(screen)
                                }
                            )
                            if (index < words.itemSnapshotList.lastIndex)
                                Divider(color = CustomTheme.colors.onBackground, thickness = 1.dp)
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
                                headersState.headers.getOrNull(headersState.selectedHeaderIndex.value)
                                    ?.asString(context)
                        }
                        scope.launch {
                            if (selectedItemIndex != -1) {
                                Timber.d(
                                    "DictList: selectedHeaderIndex = ${headersState.selectedHeaderIndex.value} selectedHeader = ${
                                        headersState.headers.getOrNull(
                                            headersState.selectedHeaderIndex.value
                                        )?.asString(context)
                                    } index = ${selectedItemIndex} item = ${
                                        words.peek(selectedItemIndex)?.word?.asString(
                                            context
                                        )
                                    }"
                                )
                                listState.scrollToItem(selectedItemIndex)
                            } else if (words.itemCount > 0) {
                                Timber.d("DictList: index = ${selectedItemIndex} itemCount = ${words.itemCount}")
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
                                        CustomTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                                    }
                                )
                            }
                        }
                    }
                }
            },
        )
    }
}