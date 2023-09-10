package apps.robot.grammar.impl.pronounce.domain

import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PronounceInitializer(
    private val grammarRepository: GrammarRepository,
    private val coroutineScope: CoroutineScope,
    private val appDispatchers: AppDispatchers
) : BaseAppInitializer {

    override fun onAppStartInit() {
        coroutineScope.launch(appDispatchers.network) {
            if (grammarRepository.getPronounceItemSize() == 0) {
                grammarRepository.loadPronounceItems()
            }
        }
    }
}