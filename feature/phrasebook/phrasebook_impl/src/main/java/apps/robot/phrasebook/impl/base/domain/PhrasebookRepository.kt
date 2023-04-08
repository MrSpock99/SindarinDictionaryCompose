package apps.robot.phrasebook.impl.base.domain

interface PhrasebookRepository {
    fun getPhrasebookCategories(): List<String>
    suspend fun getCategoryItems(categoryName: String): List<CategoryItem>
}