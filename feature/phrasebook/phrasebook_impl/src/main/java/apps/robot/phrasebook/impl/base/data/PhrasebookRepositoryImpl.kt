package apps.robot.phrasebook.impl.base.data

import android.content.res.Resources
import apps.robot.phrasebook.api.CategoryItem
import apps.robot.phrasebook.api.PhrasebookDao
import apps.robot.phrasebook.impl.R
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PhrasebookRepositoryImpl(
    private val resources: Resources,
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val dao: PhrasebookDao
) : PhrasebookRepository {

    override fun getPhrasebookCategories(): List<String> {
        return resources.getStringArray(R.array.phrasebook_categories).toList()
    }

    override fun getPhrasebookProCategories(): List<String> {
        return resources.getStringArray(R.array.phrasebook_categories_pro).toList()
    }

    override fun getCategoryItemsAsFlow(categoryName: String): Flow<List<CategoryItem>> {
        return dao.getCategoryItemsAfFlow(getMappedId(categoryName))
    }

    override suspend fun isCacheEmpty(): Boolean {
        return dao.getCategoryItemsSize() == 0
    }

    override suspend fun loadPhrasebookCategoryItems() {
        listOfCategories.forEach { categoryId ->
            val list = withContext(dispatchers.ui) {
                suspendCoroutine<List<CategoryItem?>> { emitter ->
                    db.collection(categoryId)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                emitter.resume(
                                    task.result?.documents?.map {
                                        val word = it.toObject(CategoryItem::class.java)
                                        word
                                    } ?: emptyList())
                            } else {
                                emitter.resumeWithException(
                                    task.exception ?: java.lang.Exception()
                                )
                            }
                        }
                }
            }.filterNotNull().map {
                CategoryItem(it.id, it.word, it.translation, categoryId)
            }

            dao.insertAll(list)
        }
    }

    private fun getMappedId(categoryName: String): String {
        val categories = getPhrasebookCategories()
        val mappedId = when(categoryName) {
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
            else -> ""
        }
        return mappedId
    }

    companion object {
        private val listOfCategories = listOf(
            "greetings",
            "farewells",
            "calls",
            "talking",
            "smallTalk",
            "questionsAndAnswers",
            "compliments",
            "romance",
            "tender",
            "adventure",
            "Exclamation",
            "Pleas, Entreaties",
            "trouble",
            "insults",
            "threats",
            "battle_cries",
            "battle_phrases",
            "healing",
            "professions",
            "monthsOfTheYear",
            "seasons",
            "dayOfTheWeek",
            "weather",
        )
    }
}