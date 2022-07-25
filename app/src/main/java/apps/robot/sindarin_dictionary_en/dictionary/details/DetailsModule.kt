package apps.robot.sindarin_dictionary_en.dictionary.details

import apps.robot.sindarin_dictionary_en.dictionary.base.data.ElfToEngDictionaryRepositoryImpl
import apps.robot.sindarin_dictionary_en.dictionary.base.data.EngToElfDictionaryRepositoryImpl
import apps.robot.sindarin_dictionary_en.dictionary.details.domain.DictionaryGetWordByIdUseCase
import apps.robot.sindarin_dictionary_en.dictionary.details.domain.DictionaryUpdateWordUseCase
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun detailsModule() = module {
    factory {
        DictionaryGetWordByIdUseCase(
            engToElfDictionaryRepository = get<EngToElfDictionaryRepositoryImpl>(),
            elfToEngDictionaryRepository = get<ElfToEngDictionaryRepositoryImpl>()
        )
    }
    factory {
        DictionaryUpdateWordUseCase(
            engToElfDictionaryRepository = get<EngToElfDictionaryRepositoryImpl>(),
            elfToEngDictionaryRepository = get<ElfToEngDictionaryRepositoryImpl>()
        )
    }
    viewModel {
        DetailsViewModel(
            getWordById = get(),
            setTextToClipboard = get(),
            updateWord = get()
        )
    }
}