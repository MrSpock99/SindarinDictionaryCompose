package apps.robot.phrasebook.impl.base.domain

import apps.robot.phrasebook.api.CategoryItem
import kotlinx.coroutines.flow.Flow

interface PhrasebookRepository {
    fun getPhrasebookCategories(): List<String>
    fun getPhrasebookProCategories(): List<String>
    fun getCategoryItemsAsFlow(categoryName: String): Flow<List<CategoryItem>>
    fun getCategoryItems(categoryName: String): List<CategoryItem>
    suspend fun isCacheEmpty(): Boolean
    suspend fun loadPhrasebookCategoryItems()
}