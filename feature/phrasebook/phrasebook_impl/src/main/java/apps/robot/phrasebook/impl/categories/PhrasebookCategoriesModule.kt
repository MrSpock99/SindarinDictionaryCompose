package apps.robot.phrasebook.impl.categories

import apps.robot.phrasebook.impl.categories.presentation.PhrasebookCategoriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal fun phrasebookCategoriesModule() = module {
    factory {
        PhrasebookCategoriesViewModel(
            repository = get(),
            context = androidContext(),
            dispatchers = get()
        )
    }
}