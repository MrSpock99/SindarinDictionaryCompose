package apps.robot.sindarin_dictionary_en.dictionary.list.domain

import androidx.paging.PagingData
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.ElfToEngDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.EngToElfDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import kotlinx.coroutines.flow.Flow

internal class DictionarySearchWordsUseCase(
    private val engToElfDictionaryRepository: EngToElfDictionaryRepository,
    private val elfToEngDictionaryRepository: ElfToEngDictionaryRepository
) {

    operator fun invoke(dictionaryMode: DictionaryMode, keyword: String): Flow<PagingData<Word>> {
        return when (dictionaryMode) {
            DictionaryMode.ENGLISH_TO_ELVISH -> {
                engToElfDictionaryRepository.getPagedWordsAsFlow(keyword)
            }
            DictionaryMode.ELVISH_TO_ENGLISH -> {
                elfToEngDictionaryRepository.getPagedWordsAsFlow(keyword)
            }
        }
    }
}