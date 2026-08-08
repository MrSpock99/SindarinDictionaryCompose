package apps.robot.sindarin_dictionary_en.dictionary.details

import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal fun detailsModule() = module {
    viewModel {
        DetailsViewModel(
            setTextToClipboard = get(),
            updateFavoriteStatus = get(),
            getFavoriteById = get()
        )
    }
}