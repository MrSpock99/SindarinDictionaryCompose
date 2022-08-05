package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import apps.robot.sindarin_dictionary_en.R
import apps.robot.sindarin_dictionary_en.appCurrentDestinationAsState
import apps.robot.sindarin_dictionary_en.destinations.WordDetailsDestination
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.DictionaryListViewModel
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.DictionaryListState
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.SearchWidgetState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.lang.Math.abs

@Destination(start = true)
@Composable
fun DictionaryList(viewModel: DictionaryListViewModel = getViewModel(), navigator: DestinationsNavigator) {
    val state by viewModel.state.collectAsState()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val navController = rememberNavController()
    val isTopBarVisible = when (navController.appCurrentDestinationAsState().value) {
        WordDetailsDestination -> false
        else -> true
    }
    val isUserDragging = remember {
        mutableStateOf(false)
    }
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
            color = if (isUserDragging.value) {
                colorResource(id = R.color.black).copy(alpha = ContentAlpha.medium)
            } else {
                Color.Transparent
            },
        ) {
            DictionaryListContent(
                state = state,
                isUserDragging = isUserDragging,
                navigator = navigator
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DictionaryListContent(
    state: DictionaryListState, isUserDragging: MutableState<Boolean>, navigator: DestinationsNavigator
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (wordList, selectedHeader, headerList, nothingFound) = createRefs()

        val listState = rememberLazyListState()
        val keyboardController = LocalSoftwareKeyboardController.current

        var selectedHeaderIndex by remember { mutableStateOf(-1) }
        val words = state.words.collectAsLazyPagingItems()

        if (listState.isScrollInProgress) {
            keyboardController?.hide()
        }
        if (isUserDragging.value) {
            Text(
                text = state.headers.getOrNull(selectedHeaderIndex)?.asString() ?: "",
                fontSize = 100.sp,
                color = colorResource(id = R.color.white),
                modifier = Modifier.constrainAs(selectedHeader) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        bias = 0.3F
                    )
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }

        if (words.itemCount == 0 && state.searchWidgetState == SearchWidgetState.OPENED) {
            Text(
                text = stringResource(id = R.string.dictionary_list_nothing_found),
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                modifier = Modifier.constrainAs(nothingFound) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
            )
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .constrainAs(wordList) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(headerList.start)
                }
                .padding(end = 16.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 40.dp,
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
                            navigator.navigate(WordDetailsDestination(it.id, state.dictionaryMode))
                        }
                    )
                }
            }
        }
        val offsets = remember { mutableStateMapOf<Int, Float>() }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        fun updateSelectedIndexIfNeeded(offset: Float): Boolean {
            val index = offsets
                .mapValues { abs(it.value - offset) }
                .entries
                .minByOrNull { it.value }
                ?.key ?: return false
            selectedHeaderIndex = index

            val selectedItemIndex = words.itemSnapshotList.indexOfFirst {
                it?.word?.asString(context)?.first()?.uppercase() ==
                    state.headers.getOrNull(selectedHeaderIndex)?.asString(context)
            }
            scope.launch {
                if (selectedItemIndex != -1) {
                    listState.scrollToItem(selectedItemIndex)
                } else if (words.itemCount > 0) {
                    listState.scrollToItem(words.itemCount - 1)
                }
            }.invokeOnCompletion {
                if (isUserDragging.value.not()) {
                    selectedHeaderIndex = -1
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
                .constrainAs(headerList) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
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
                            isUserDragging.value = true
                        }, onDragEnd = {
                            selectedHeaderIndex = -1
                            isUserDragging.value = false
                        })
                }
        ) {
            state.headers.forEachIndexed { i, header ->
                val isHeaderSelected = isUserDragging.value && i == selectedHeaderIndex
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