package apps.robot.phrasebook.impl.base.data

import android.content.res.Resources
import apps.robot.phrasebook.api.CategoryItem
import apps.robot.phrasebook.api.PhrasebookDao
import apps.robot.phrasebook.impl.R
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import kotlinx.coroutines.flow.Flow

internal class PhrasebookRepositoryImpl(
    private val resources: Resources,
    private val dataSource: PhrasebookDataSource,
    private val dao: PhrasebookDao
) : PhrasebookRepository {

    override fun getPhrasebookCategories(): List<String> {
        return resources.getStringArray(R.array.phrasebook_categories).toList()
    }

    override fun getPhrasebookProCategories(): List<String> {
        return resources.getStringArray(R.array.phrasebook_categories_pro).toList()
    }

    override fun getCategoryItemsAsFlow(categoryName: String): Flow<List<CategoryItem>> {
        return dao.getCategoryItemsAsFlow(getMappedId(categoryName))
    }

    override fun getCategoryItems(categoryName: String): List<CategoryItem> {
        return dao.getCategoryItems(categoryName)
    }

    override suspend fun isCacheEmpty(): Boolean {
        return dao.getCategoryItemsSize() == 0
    }

    override suspend fun loadPhrasebookCategoryItems() {
        dao.insertAll(dataSource.loadItems())
    }

    private fun getMappedId(categoryName: String): String {
        val categories = getPhrasebookCategories()
        val mappedId = when (categoryName) {
            categories[0] -> "greetings"
            categories[1] -> "farewells"
            categories[2] -> "calls"
            categories[3] -> "talking"
            categories[4] -> "smallTalk"
            categories[5] -> "questionsAndAnswers"
            categories[6] -> "compliments"
            categories[7] -> "romance"
            categories[8] -> "tender"
            categories[9] -> "adventure"
            categories[10] -> "Exclamation"
            categories[11] -> "Pleas, Entreaties"
            categories[12] -> "trouble"
            categories[13] -> "insults"
            categories[14] -> "threats"
            categories[15] -> "battle_cries"
            categories[16] -> "battle_phrases"
            categories[17] -> "healing"
            categories[18] -> "professions"
            categories[19] -> "monthsOfTheYear"
            categories[20] -> "seasons"
            categories[21] -> "dayOfTheWeek"
            categories[22] -> "weather"
            categories[23] -> "colors"
            else -> ""
        }
        return mappedId
    }

}
