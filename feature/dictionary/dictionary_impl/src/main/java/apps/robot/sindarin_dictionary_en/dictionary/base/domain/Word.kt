package apps.robot.sindarin_dictionary_en.dictionary.base.domain

import com.google.gson.annotations.SerializedName

data class Word(
    var id: String,
    val word: String,
    val translation: String,
    @SerializedName("is_favorite")
    val isFavorite: Boolean
) {
    constructor() : this("","", "", false)
}
