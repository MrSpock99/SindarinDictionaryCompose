package apps.robot.sindarin_dictionary_en.dictionary.list.domain

import apps.robot.sindarin_dictionary_en.dictionary.base.domain.DictionaryRepository

class DictionaryGetHeadersUseCase(
    private val elfToEngRepository: DictionaryRepository,
    private val engToElfRepository: DictionaryRepository
) {
    suspend operator fun invoke(mode: DictionaryMode): List<String> {
        return when (mode) {
            DictionaryMode.ENGLISH_TO_ELVISH -> {
                engToElfRepository.getAllWords()
            }
            DictionaryMode.ELVISH_TO_ENGLISH -> {
                elfToEngRepository.getAllWords()
            }
        }.map { it.word.first().uppercase() }.distinct()
    }
}