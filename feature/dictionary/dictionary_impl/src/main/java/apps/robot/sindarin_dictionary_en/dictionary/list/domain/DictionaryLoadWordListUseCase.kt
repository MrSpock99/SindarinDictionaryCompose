package apps.robot.sindarin_dictionary_en.dictionary.list.domain

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository

class DictionaryLoadWordListUseCase(
    private val dictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(mode: DictionaryMode) {
        dictionaryRepository.loadWords(mode)
    }
}