package apps.robot.sindarin_dictionary_en.dictionary.details.domain

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode

internal class DictionaryGetWordByIdUseCase(
    private val engToElfDictionaryRepository: DictionaryRepository,
    private val elfToEngDictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(wordId: String, mode: DictionaryMode): Word {
        return when (mode) {
            DictionaryMode.ENGLISH_TO_ELVISH -> {
                engToElfDictionaryRepository.getWordById(wordId)
            }
            DictionaryMode.ELVISH_TO_ENGLISH -> {
                elfToEngDictionaryRepository.getWordById(wordId)
            }
        }
    }
}