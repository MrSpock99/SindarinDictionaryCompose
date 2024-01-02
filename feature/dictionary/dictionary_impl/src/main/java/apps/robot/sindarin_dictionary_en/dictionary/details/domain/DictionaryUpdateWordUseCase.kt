package apps.robot.sindarin_dictionary_en.dictionary.details.domain

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word

internal class DictionaryUpdateWordUseCase(
    private val dictionaryRepository: DictionaryRepository,
) {
    suspend operator fun invoke(word: Word?, dictionaryMode: DictionaryMode) {
        requireNotNull(word)
        dictionaryRepository.updateWord(dictionaryMode, word)
    }
}