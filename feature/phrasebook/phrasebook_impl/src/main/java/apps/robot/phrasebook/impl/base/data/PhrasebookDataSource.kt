package apps.robot.phrasebook.impl.base.data

import apps.robot.phrasebook.api.CategoryItem

internal interface PhrasebookDataSource {
    suspend fun loadItems(): List<CategoryItem>
}
