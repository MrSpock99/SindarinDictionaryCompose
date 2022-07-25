package apps.robot.sindarin_dictionary_en.dictionary.details.domain

import apps.robot.sindarin_dictionary_en.dictionary.base.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode

class DictionaryGetWordByIdUseCase(
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