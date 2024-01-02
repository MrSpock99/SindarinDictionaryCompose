package apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity.Companion.ELF_TO_ENG_WORDS_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = ELF_TO_ENG_WORDS_TABLE)
class ElfToEngWordEntity(
    @PrimaryKey
    var id: String,
    override var word: String,
    var translation: String,
    @SerializedName("is_favorite")
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean
): WordEntity {
    constructor() : this("","", "", false)

    companion object {
        const val ELF_TO_ENG_WORDS_TABLE = "elf_to_eng_words"
    }
}