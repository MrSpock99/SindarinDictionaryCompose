package apps.robot.sindarin_dictionary_en.dictionary.list.domain

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.LoadStrategy

internal class DictionaryLoadWordListUseCase(
    private val elfToEngRepository: DictionaryRepository,
    private val engToElfRepository: DictionaryRepository
) {
    suspend operator fun invoke(mode: DictionaryMode, loadStrategy: LoadStrategy) {
        if (mode == DictionaryMode.ELVISH_TO_ENGLISH) {
            elfToEngRepository.loadWords(loadStrategy)
        } else {
            engToElfRepository.loadWords(loadStrategy)
        }
    }
}