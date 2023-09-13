package apps.robot.phrasebook.impl

import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PhrasebookInitializer(
    private val repository: PhrasebookRepository,
    private val coroutineScope: CoroutineScope,
    private val appDispatchers: AppDispatchers
): BaseAppInitializer {

    override fun onAppStartInit() {
        coroutineScope.launch(appDispatchers.network) {
            if (repository.isCacheEmpty()) {
                repository.loadPhrasebookCategoryItems()
            }
        }
    }
}