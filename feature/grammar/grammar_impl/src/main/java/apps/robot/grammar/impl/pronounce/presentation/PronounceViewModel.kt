package apps.robot.grammar.impl.pronounce.presentation

import apps.robot.grammar.impl.pronounce.domain.GrammarRepository
import apps.robot.grammar.impl.pronounce.domain.PronounceItem
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class PronounceViewModel(
    private val grammarRepository: GrammarRepository
): BaseViewModel() {
    val uiState = MutableStateFlow(PronounceUiState())

    init {
        launchJob {
            uiState.value = PronounceUiState(grammarRepository.getPronunciation())
        }
    }


    data class PronounceUiState(val items: List<PronounceItem> = emptyList())
}