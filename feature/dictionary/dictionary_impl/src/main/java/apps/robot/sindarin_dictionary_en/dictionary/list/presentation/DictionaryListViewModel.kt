package apps.robot.sindarin_dictionary_en.dictionary.list.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.Content
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.Loading
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.SearchWidgetState
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetHeadersUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetPagedWordListAsFlowUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryLoadWordListUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionarySearchWordsUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.DictionaryListState
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.WordUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import timber.log.Timber

internal class DictionaryListViewModel(
    private val getPagedWordListAsFlow: DictionaryGetPagedWordListAsFlowUseCase,
    private val loadWordList: DictionaryLoadWordListUseCase,
    private val getHeaders: DictionaryGetHeadersUseCase,
    private val searchWords: DictionarySearchWordsUseCase,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    val state = MutableStateFlow(DictionaryListState(uiState = Loading))

    init {
        launchJob {
            listOf(
                asyncCatching { loadWordList(DictionaryMode.ELVISH_TO_ENGLISH) },
                asyncCatching { loadWordList(DictionaryMode.ENGLISH_TO_ELVISH) }
            ).awaitAll()
            subscribeToWords(state.value.dictionaryMode)
            subscribeToSearch()
            state.update { it.copy(uiState = Content) }
        }
        //setHeaders(state.value.dictionaryMode)
    }

    fun onModeChange(dictionaryMode: DictionaryMode) {
        state.update {
            it.copy(
                dictionaryMode = dictionaryMode
            )
        }
        subscribeToWords(dictionaryMode)
        //setHeaders(dictionaryMode)
    }

    fun onSearchToggle() {
        state.update {
            it.copy(
                searchWidgetState = if (state.value.searchWidgetState == SearchWidgetState.OPENED) {
                    SearchWidgetState.CLOSED
                } else {
                    SearchWidgetState.OPENED
                }
            )
        }
    }

    fun onSearchTextChange(searchText: String) {
        state.value.searchText.tryEmit(searchText)
    }

    fun onDragChange(isDragging: Boolean) {
        state.value.headersState.isUserDragging.value = isDragging
        state.value.headersState.shouldShowSelectedHeader.value =
            isDragging && state.value.headersState.headers.isNotEmpty()
    }

    fun onSelectedHeaderIndexChange(selectedIndex: Int) {
        state.value.headersState.selectedHeaderIndex.value = selectedIndex
    }

    private fun subscribeToWords(dictionaryMode: DictionaryMode) {
        state.value = state.value.copy(
            words = getPagedWordListAsFlow(dictionaryMode).map { pagingData ->
                pagingData.map {
                    Timber.d("DictList: ${it.word}")
                    WordUiModel(
                        id = it.id,
                        word = UiText.DynamicString(it.word),
                        translation = UiText.DynamicString(it.translation),
                        isFavorite = it.isFavorite
                    )
                }
            }.cachedIn(viewModelScope + dispatchers.computing)
        )
    }

    private fun setHeaders(dictionaryMode: DictionaryMode) {
        launchJob {
            state.update {
                it.copy(
                    headersState = it.headersState.copy(
                        headers = getHeaders(dictionaryMode).map {
                            UiText.DynamicString(
                                it
                            )
                        }
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun subscribeToSearch() {
        state.value.searchText.debounce(SEARCH_DEBOUNCE)
            .mapLatest {
                state.value = state.value.copy(
                    words = searchWords(dictionaryMode = state.value.dictionaryMode, keyword = it)
                        .map { pagingData ->
                            pagingData.map {
                                WordUiModel(
                                    id = it.id,
                                    word = UiText.DynamicString(it.word),
                                    translation = UiText.DynamicString(it.translation),
                                    isFavorite = it.isFavorite
                                )
                            }
                        }.cachedIn(viewModelScope + dispatchers.computing)
                )
            }.launchIn(viewModelScope + dispatchers.computing)
    }

    private companion object {
        const val SEARCH_DEBOUNCE = 300L
    }
}