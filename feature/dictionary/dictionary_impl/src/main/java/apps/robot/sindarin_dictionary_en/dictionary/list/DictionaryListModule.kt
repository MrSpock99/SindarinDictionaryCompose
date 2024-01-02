package apps.robot.sindarin_dictionary_en.dictionary.list

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
            dictionaryRepository = get()
        )
    }

    factory {
        DictionaryLoadWordListUseCase(
            dictionaryRepository = get()
        )
    }

    factory {
        DictionaryGetHeadersUseCase(
            dictionaryRepository = get()
        )
    }

    factory {
        DictionarySearchWordsUseCase(
            dictionaryRepository = get()
        )
    }
    viewModel {
        DictionaryListViewModel(
            getPagedWordListAsFlow = get(),
            dispatchers = get(),
            getHeaders = get(),
            searchWords = get()
        )
    }
}