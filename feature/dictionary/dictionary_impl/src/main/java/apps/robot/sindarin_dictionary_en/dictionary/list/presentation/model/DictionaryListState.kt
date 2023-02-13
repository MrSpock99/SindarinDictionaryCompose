package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model

import androidx.paging.PagingData
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

internal data class DictionaryListState(
    val words: Flow<PagingData<WordUiModel>> = emptyFlow(),
    val uiState: UiState,
    val dictionaryMode: DictionaryMode = DictionaryMode.ELVISH_TO_ENGLISH,
    val headersState: DictionaryHeadersState = DictionaryHeadersState(),
    val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,
    val searchText: MutableStateFlow<String> = MutableStateFlow(""),
)

data class DictionaryHeadersState(
    val headers: List<UiText> = emptyList(),
    val isUserDragging: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val shouldShowSelectedHeader: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val selectedHeaderIndex: MutableStateFlow<Int> = MutableStateFlow(-1)
)