package apps.robot.sindarin_dictionary_en.dictionary.api.data.local

import androidx.room.Dao
import androidx.room.Query
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity.Companion.ELF_TO_ENG_WORDS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ElfToEngDao: DictionaryDao<ElfToEngWordEntity> {
    @Query("SELECT * FROM $ELF_TO_ENG_WORDS_TABLE LIMIT :size OFFSET :offset")
    abstract override fun getPagedWords(size: Int, offset: Int): List<ElfToEngWordEntity>

    @Query("SELECT * FROM $ELF_TO_ENG_WORDS_TABLE")
    abstract override suspend fun getAllWords(): List<ElfToEngWordEntity>

    @Query("SELECT * FROM $ELF_TO_ENG_WORDS_TABLE WHERE id=:id")
    abstract override suspend fun getWordById(id: String): ElfToEngWordEntity

    @Query("SELECT * FROM $ELF_TO_ENG_WORDS_TABLE WHERE is_favorite=1")
    abstract override fun getFavoriteWordsAsFlow(): Flow<List<ElfToEngWordEntity>>
}