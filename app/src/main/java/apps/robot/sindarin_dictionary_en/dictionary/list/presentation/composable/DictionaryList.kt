package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import apps.robot.sindarin_dictionary_en.R
import apps.robot.sindarin_dictionary_en.appCurrentDestinationAsState
import apps.robot.sindarin_dictionary_en.destinations.WordDetailsDestination
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.DictionaryListViewModel
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.WordUiModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.lang.Math.abs

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
    Scaffold(
        topBar = {
            AnimatedVisibility(visible = isTopBarVisible) {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.surface
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(id = R.string.top_bar_dictionary_title),
                        fontSize = 22.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(
                        modifier = Modifier.weight(
                            weight = 1f,
                            fill = true
                        )
                    )
                    DictionaryModeToggle(owner = viewModelStoreOwner)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    ) {
        Row {
            val listState = rememberLazyListState()
            var selectedHeaderIndex by remember { mutableStateOf(-1) }
            val words = state.words.collectAsLazyPagingItems()
            var oldItemCount = -1
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .weight(1F)
            ) {
                items(
                    count = words.itemCount,
                    key = { index ->
                        words[index]?.id ?: ""
                    }) { index ->
                    if (oldItemCount != words.itemCount) {
                        Log.d("DictList", "new words arrived ${words.itemCount}")
                    }
                    oldItemCount = words.itemCount
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

            var isDragging by remember {
                mutableStateOf(false)
            }
            val context = LocalContext.current

            fun updateSelectedIndexIfNeeded(offset: Float): Boolean {
                val index = offsets
                    .mapValues { abs(it.value - offset) }
                    .entries
                    .minByOrNull { it.value }
                    ?.key ?: return false
                if (selectedHeaderIndex == index) return false
                selectedHeaderIndex = index

                val selectedItemIndex = words.itemSnapshotList.indexOfFirst {
                    it?.word?.asString(context)?.first()?.uppercase() ==
                        state.headers.getOrNull(selectedHeaderIndex)?.asString(context)
                }
                scope.launch {
                    if (selectedItemIndex != -1) {
                        listState.scrollToItem(selectedItemIndex)
                    } else if (words.itemCount > 0) {
                        Log.d("DictList", "scroll to ${words.itemCount}")
                        listState.scrollToItem(words.itemCount - 1)
                    }
                }.invokeOnCompletion {
                    if (isDragging.not()) {
                        selectedHeaderIndex = -1
                    }
                }
                return true
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.1F)
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
                                isDragging = true
                            }, onDragEnd = {
                                selectedHeaderIndex = -1
                                isDragging = false
                            })
                    }
            ) {
                state.headers.forEachIndexed { i, header ->
                    Text(
                        text = header.asString(),
                        fontSize = if (i == selectedHeaderIndex) {
                            25.sp
                        } else {
                            15.sp
                        },
                        modifier = Modifier
                            .onGloballyPositioned {
                                offsets[i] = it.boundsInParent().center.y
                            }
                            .animateContentSize()
                    )
                }
            }
        }
    }
}

@Composable
fun WordItem(wordUiModel: WordUiModel, onClick: (WordUiModel) -> Unit) {
    Text(
        text = wordUiModel.word.asString(),
        color = MaterialTheme.colors.onBackground,
        fontSize = 32.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(wordUiModel)
            }
    )
}

