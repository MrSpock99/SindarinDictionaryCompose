package apps.robot.sindarin_dictionary_en.dictionary.list.domain

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository

internal class DictionaryGetHeadersUseCase(
    private val dictionaryRepository: DictionaryRepository,
) {
    suspend operator fun invoke(mode: DictionaryMode): List<String> {
        return dictionaryRepository.getAllWords(mode).map { it.word.first().uppercase() }.distinct()
    }
}