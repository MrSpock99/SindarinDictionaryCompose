package apps.robot.sindarin_dictionary_en.dictionary.list.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import apps.robot.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base.Content
import apps.robot.sindarin_dictionary_en.base.Loading
import apps.robot.sindarin_dictionary_en.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetHeadersUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetPagedWordListAsFlowUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryLoadWordListUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.DictionaryListState
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.WordUiModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.plus

class DictionaryListViewModel(
    private val getPagedWordListAsFlow: DictionaryGetPagedWordListAsFlowUseCase,
    private val loadWordList: DictionaryLoadWordListUseCase,
    private val getHeaders: DictionaryGetHeadersUseCase,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    val state = MutableStateFlow(DictionaryListState(uiState = Loading))

    init {
        launchJob {
            state.value = state.value.copy(uiState = Loading)
            listOf(
                asyncCatching { loadWordList(DictionaryMode.ELVISH_TO_ENGLISH) },
                asyncCatching { loadWordList(DictionaryMode.ENGLISH_TO_ELVISH) }
            ).awaitAll()
            state.value = state.value.copy(uiState = Content)
        }
        subscribeToWords(state.value.dictionaryMode)
        setHeaders(state.value.dictionaryMode)
    }

    fun onModeChange(dictionaryMode: DictionaryMode) {
       /* launchJob {
            state.emit(
                state.value.copy(
                    dictionaryMode = dictionaryMode,
                    headers = getHeaders(dictionaryMode).map { UiText.DynamicString(it) }
                )
            )
        }*/
        state.value = state.value.copy(
            dictionaryMode = dictionaryMode
        )
        subscribeToWords(dictionaryMode)
        setHeaders(dictionaryMode)
    }

    private fun subscribeToWords(dictionaryMode: DictionaryMode) {
        state.value = state.value.copy(
            words = getPagedWordListAsFlow(dictionaryMode).map { pagingData ->
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
    }

    private fun setHeaders(dictionaryMode: DictionaryMode) {
        launchJob {
            state.emit(
                state.value.copy(
                    headers = getHeaders(dictionaryMode).map { UiText.DynamicString(it) }
                )
            )
        }
    }
}