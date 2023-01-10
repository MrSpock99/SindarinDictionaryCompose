package apps.robot.sindarin_dictionary_en.dictionary.list.domain

import androidx.paging.PagingData
import apps.robot.sindarin_dictionary_en.dictionary.base.data.ElfToEngDictionaryRepositoryImpl
import apps.robot.sindarin_dictionary_en.dictionary.base.data.EngToElfDictionaryRepositoryImpl
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word
import kotlinx.coroutines.flow.Flow

internal class DictionarySearchWordsUseCase(
    private val engToElfDictionaryRepository: EngToElfDictionaryRepositoryImpl,
    private val elfToEngDictionaryRepository: ElfToEngDictionaryRepositoryImpl
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