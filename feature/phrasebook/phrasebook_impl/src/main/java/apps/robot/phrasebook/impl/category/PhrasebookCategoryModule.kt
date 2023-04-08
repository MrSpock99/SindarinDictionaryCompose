package apps.robot.phrasebook.impl.category

import apps.robot.phrasebook.impl.category.presentation.PhrasebookCategoryViewModel
import org.koin.dsl.module

internal fun phrasebookCategoriesModule() = module {
    factory {
        PhrasebookCategoryViewModel(
            repository = get(),
            dispatchers = get()
        )
    }
}