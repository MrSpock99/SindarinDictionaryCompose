package apps.robot.sindarin_dictionary_en.dictionary.base.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

internal interface DictionaryRepository {
    suspend fun loadWords()
    fun getPagedWordsAsFlow(keyword: String? = null): Flow<PagingData<Word>>
    suspend fun getAllWords(): List<Word>
    suspend fun getWordById(id: String): Word
    suspend fun updateWord(word: Word)
}