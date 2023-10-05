package apps.robot.phrasebook.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import apps.robot.phrasebook.api.CategoryItem.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class CategoryItem(@PrimaryKey var id: String, var word: String, var translation: String, var category: String) {
    constructor() : this("","","", "")

    companion object {
        const val TABLE_NAME = "category_item"
    }
}