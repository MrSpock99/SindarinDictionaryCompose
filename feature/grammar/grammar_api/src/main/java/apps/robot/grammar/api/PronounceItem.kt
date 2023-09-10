package apps.robot.grammar.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PronounceDao.PRONOUNCE_TABLE)
data class PronounceItem(
    @PrimaryKey val id: String,
    val sound: String,
    val example: String,
    val sindarinExample: String
) {
    constructor() : this("", "", "", "")
}