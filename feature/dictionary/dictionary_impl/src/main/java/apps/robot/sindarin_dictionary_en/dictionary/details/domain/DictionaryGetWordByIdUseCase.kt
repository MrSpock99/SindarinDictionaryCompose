package apps.robot.sindarin_dictionary_en.dictionary.details.domain

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word

internal class DictionaryGetWordByIdUseCase(
    private val dictionaryRepository: DictionaryRepository,
) {
    suspend operator fun invoke(wordId: String, mode: DictionaryMode): Word {
        return dictionaryRepository.getWordById(mode, wordId)
    }
}