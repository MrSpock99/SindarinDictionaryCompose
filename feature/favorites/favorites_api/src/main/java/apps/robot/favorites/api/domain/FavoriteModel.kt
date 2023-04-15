package apps.robot.favorites.api.domain

import androidx.room.Entity
import apps.robot.favorites.api.domain.FavoriteModel.Companion.FAVORITES_TABLE

@Entity(tableName = FAVORITES_TABLE, primaryKeys = ["text", "translation"])
class FavoriteModel(
    var id: String?,
    val text: String,
    val translation: String,
) {
    constructor() : this("", "", "")

    companion object {
        const val FAVORITES_TABLE = "favorites_table"
    }
}