package apps.robot.sindarin_dictionary_en.dictionary.list.domain

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository

class DictionaryLoadWordListUseCase(
    private val elfToEngRepository: DictionaryRepository,
    private val engToElfRepository: DictionaryRepository
) {
    suspend operator fun invoke(mode: DictionaryMode) {
        if (mode == DictionaryMode.ELVISH_TO_ENGLISH) {
            elfToEngRepository.loadWords()
        } else {
            engToElfRepository.loadWords()
        }
    }
}