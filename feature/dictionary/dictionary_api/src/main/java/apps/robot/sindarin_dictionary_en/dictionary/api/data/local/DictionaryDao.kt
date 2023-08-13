package apps.robot.sindarin_dictionary_en.dictionary.api.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<T>)

    fun getPagedWords(size: Int, offset: Int): List<@JvmSuppressWildcards T>

    suspend fun getAllWords(): List<@JvmSuppressWildcards T>

    fun getFavoriteWordsAsFlow(): Flow<List<@JvmSuppressWildcards T>>

    suspend fun getWordById(id: String): T

    @Update
    suspend fun update(wordEntity: T)

    suspend fun deleteAll()
}