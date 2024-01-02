package apps.robot.sindarin_dictionary_en.dictionary.base

import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryLoadWordListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DictionaryInitializer(
    private val loadWordList: DictionaryLoadWordListUseCase,
    private val coroutineScope: CoroutineScope,
    private val repository: DictionaryRepository,
    private val dispatchers: AppDispatchers
) : BaseAppInitializer {

    override fun onAppStartInit() {
        coroutineScope.launch(dispatchers.network) {
            if (repository.getWordsSize() == 0) {
                listOf(
                    async { loadWordList(DictionaryMode.ELVISH_TO_ENGLISH) },
                    async { loadWordList(DictionaryMode.ENGLISH_TO_ELVISH) }
                ).awaitAll()
                areWordsLoaded.value = true
            } else {
                areWordsLoaded.value = true
            }
        }
    }

    companion object {
        val areWordsLoaded = MutableStateFlow(false)
    }
}