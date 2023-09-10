package apps.robot.grammar.impl.plural.presentation

import apps.robot.grammar.impl.plural.domain.PluralItem
import apps.robot.grammar.impl.pronounce.domain.GrammarRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class PluralViewModel(
    private val grammarRepository: GrammarRepository
) : BaseViewModel() {
    val uiState = MutableStateFlow(PluralUiState())

    init {
        launchJob {
            grammarRepository.getPluralItemsAsFlow().collect {
                uiState.value = PluralUiState(it)
            }
        }
    }

    data class PluralUiState(val items: List<PluralItem> = emptyList())
}
