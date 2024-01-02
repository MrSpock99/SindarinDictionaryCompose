package apps.robot.sindarin_dictionary_en.dictionary.api.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun loadWords(dictionaryMode: DictionaryMode)
    fun getPagedWordsAsFlow(dictionaryMode: DictionaryMode, keyword: String? = null): Flow<PagingData<Word>>
    suspend fun getAllWords(dictionaryMode: DictionaryMode): List<Word>
    suspend fun getWordById(dictionaryMode: DictionaryMode, id: String): Word
    suspend fun updateWord(dictionaryMode: DictionaryMode, word: Word)
    fun getFavoriteWordsAsFlow(dictionaryMode: DictionaryMode): Flow<List<Word>>
    fun getWordsSize(): Int
}