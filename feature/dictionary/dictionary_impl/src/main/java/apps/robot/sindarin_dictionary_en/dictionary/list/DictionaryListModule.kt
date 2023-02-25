package apps.robot.sindarin_dictionary_en.dictionary.list

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.ElfToEngDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.EngToElfDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetHeadersUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryGetPagedWordListAsFlowUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryLoadWordListUseCase
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionarySearchWordsUseCase
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
            elfToEngRepository = get<ElfToEngDictionaryRepository>(),
            engToElfRepository = get<EngToElfDictionaryRepository>()
        )
    }

    factory {
        DictionaryGetHeadersUseCase(
            elfToEngRepository = get<ElfToEngDictionaryRepository>(),
            engToElfRepository = get<EngToElfDictionaryRepository>()
        )
    }

    factory {
        DictionarySearchWordsUseCase(
            engToElfDictionaryRepository = get(),
            elfToEngDictionaryRepository = get()
        )
    }
    viewModel {
        DictionaryListViewModel(
            getPagedWordListAsFlow = get(),
            dispatchers = get(),
            loadWordList = get(),
            getHeaders = get(),
            searchWords = get()
        )
    }
}