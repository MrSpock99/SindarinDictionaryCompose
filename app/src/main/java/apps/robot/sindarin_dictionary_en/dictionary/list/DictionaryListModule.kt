package apps.robot.sindarin_dictionary_en.dictionary.list

import apps.robot.sindarin_dictionary_en.dictionary.base.data.ElfToEngDictionaryRepositoryImpl
import apps.robot.sindarin_dictionary_en.dictionary.base.data.EngToElfDictionaryRepositoryImpl
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetHeadersUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetPagedWordListAsFlowUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryLoadWordListUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.DictionaryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal fun dictionaryListModule() = module {
    factory {
        DictionaryGetPagedWordListAsFlowUseCase(
            engToElfDictionaryRepository = get(),
            elfToEngDictionaryRepository = get()
        )
    }

    factory {
        DictionaryLoadWordListUseCase(
            elfToEngRepository = get<ElfToEngDictionaryRepositoryImpl>(),
            engToElfRepository = get<EngToElfDictionaryRepositoryImpl>()
        )
    }

    factory {
        DictionaryGetHeadersUseCase(
            elfToEngRepository = get<ElfToEngDictionaryRepositoryImpl>(),
            engToElfRepository = get<EngToElfDictionaryRepositoryImpl>()
        )
    }

    viewModel {
        DictionaryListViewModel(
            getPagedWordListAsFlow = get(),
            dispatchers = get(),
            loadWordList = get(),
            getHeaders = get()
        )
    }
}