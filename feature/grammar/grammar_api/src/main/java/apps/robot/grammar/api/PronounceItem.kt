package apps.robot.grammar.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PronounceDao.PRONOUNCE_TABLE)
data class PronounceItem(
    @PrimaryKey var id: String,
    var sound: String,
    var example: String,
    var sindarinExample: String
) {
    constructor() : this("", "", "", "")
}