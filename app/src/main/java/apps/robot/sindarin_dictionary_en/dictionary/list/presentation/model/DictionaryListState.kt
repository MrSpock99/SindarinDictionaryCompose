package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model

import androidx.paging.PagingData
import apps.robot.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base.UiState
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

data class DictionaryListState(
    val words: Flow<PagingData<WordUiModel>> = emptyFlow(),
    val headers: List<UiText> = emptyList(),
    val uiState: UiState,
    val dictionaryMode: DictionaryMode = DictionaryMode.ENGLISH_TO_ELVISH,
    val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,
    val searchText: MutableStateFlow<String> = MutableStateFlow("")
)