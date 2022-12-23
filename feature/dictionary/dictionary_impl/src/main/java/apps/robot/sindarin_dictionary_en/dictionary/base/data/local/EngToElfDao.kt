package apps.robot.sindarin_dictionary_en.dictionary.base.data.local

import androidx.room.Dao
import androidx.room.Query
import apps.robot.sindarin_dictionary_en.dictionary.base.data.local.model.EngToElfWordEntity

@Dao
abstract class EngToElfDao : DictionaryDao<EngToElfWordEntity> {
    @Query("SELECT * FROM ${EngToElfWordEntity.ENG_TO_ELF_WORDS_TABLE} LIMIT :size OFFSET :offset")
    abstract override fun getPagedWords(size: Int, offset: Int): List<EngToElfWordEntity>

    @Query("SELECT * FROM ${EngToElfWordEntity.ENG_TO_ELF_WORDS_TABLE}")
    abstract override suspend fun getAllWords(): List<EngToElfWordEntity>

    @Query("SELECT * FROM ${EngToElfWordEntity.ENG_TO_ELF_WORDS_TABLE} WHERE id=:id")
    abstract override suspend fun getWordById(id: String): EngToElfWordEntity

    @Query("SELECT * FROM ${EngToElfWordEntity.ENG_TO_ELF_WORDS_TABLE} WHERE is_favorite=1")
    abstract override suspend fun getFavoriteWords(): List<EngToElfWordEntity>
}