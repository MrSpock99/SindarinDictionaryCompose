package apps.robot.sindarin_dictionary_en.dictionary.api.data.local

import androidx.room.Dao
import androidx.room.Query
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity.Companion.ENG_TO_ELF_WORDS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EngToElfDao : DictionaryDao<EngToElfWordEntity> {
    @Query("SELECT * FROM $ENG_TO_ELF_WORDS_TABLE LIMIT :size OFFSET :offset")
    abstract override fun getPagedWords(size: Int, offset: Int): List<EngToElfWordEntity>

    @Query("SELECT * FROM $ENG_TO_ELF_WORDS_TABLE")
    abstract override suspend fun getAllWords(): List<EngToElfWordEntity>

    @Query("SELECT * FROM $ENG_TO_ELF_WORDS_TABLE WHERE id=:id")
    abstract override suspend fun getWordById(id: String): EngToElfWordEntity

    @Query("SELECT * FROM $ENG_TO_ELF_WORDS_TABLE WHERE is_favorite=1")
    abstract override fun getFavoriteWordsAsFlow(): Flow<List<EngToElfWordEntity>>

    @Query("SELECT COUNT(*) FROM $ENG_TO_ELF_WORDS_TABLE")
    abstract override fun getWordsSize(): Int
}