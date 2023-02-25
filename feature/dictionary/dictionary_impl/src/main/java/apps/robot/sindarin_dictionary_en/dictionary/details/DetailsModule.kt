package apps.robot.sindarin_dictionary_en.dictionary.details

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.ElfToEngDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.EngToElfDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.details.domain.DictionaryGetWordByIdUseCase
import apps.robot.sindarin_dictionary_en.dictionary.details.domain.DictionaryUpdateWordUseCase
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal fun detailsModule() = module {
    factory {
        DictionaryGetWordByIdUseCase(
            engToElfDictionaryRepository = get<EngToElfDictionaryRepository>(),
            elfToEngDictionaryRepository = get<ElfToEngDictionaryRepository>()
        )
    }
    factory {
        DictionaryUpdateWordUseCase(
            engToElfDictionaryRepository = get<EngToElfDictionaryRepository>(),
            elfToEngDictionaryRepository = get<ElfToEngDictionaryRepository>()
        )
    }
    viewModel {
        DetailsViewModel(
            setTextToClipboard = get(),
            updateFavoriteStatus = get(),
            getFavoriteById = get()
        )
    }
}