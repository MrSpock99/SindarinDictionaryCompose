package apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity.Companion.ENG_TO_ELF_WORDS_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = ENG_TO_ELF_WORDS_TABLE)
class EngToElfWordEntity(
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
        const val ENG_TO_ELF_WORDS_TABLE = "eng_to_elf_words"
    }
}