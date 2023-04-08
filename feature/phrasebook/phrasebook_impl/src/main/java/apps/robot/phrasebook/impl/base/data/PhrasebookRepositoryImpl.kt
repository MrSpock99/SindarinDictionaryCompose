package apps.robot.phrasebook.impl.base.data

import android.content.res.Resources
import apps.robot.phrasebook.impl.R
import apps.robot.phrasebook.impl.base.domain.CategoryItem
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PhrasebookRepositoryImpl(
    private val resources: Resources,
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers
) : PhrasebookRepository {

    override fun getPhrasebookCategories(): List<String> {
        return resources.getStringArray(R.array.phrasebook_categories).toList()
    }

    override suspend fun getCategoryItems(categoryName: String): List<CategoryItem> {
        val categories = getPhrasebookCategories()
        val mappedId = when(categoryName) {
            categories[0] -> "greetings"
            categories[1] -> "farewells"
            categories[2] -> "calls"
            categories[3] -> "talking"
            categories[4] -> "smalltalk"
            categories[5] -> "questionsAndAnswers"
            categories[6] -> "compliments"
            categories[7] -> "romance"
            categories[8] -> "tender"
            categories[9] -> "adventure"
            categories[10] -> "Exclamation"
            categories[11] -> "Pleas, Entreaties" // надо в базу загрузить
            categories[12] -> "trouble"
            categories[13] -> "insults"
            categories[14] -> "threats"
            categories[15] -> "battle_cries"
            categories[16] -> "battle_phrases"
            categories[17] -> "healing"
            categories[18] -> "professions"
            categories[19] -> "monthsOfTheYear"
            categories[20] -> "seasons"
            categories[22] -> "dayOfTheWeek"
            categories[23] -> "weather"
            else -> ""
        }
        val list = withContext(dispatchers.ui) {
            suspendCoroutine<List<CategoryItem?>> { emitter ->
                db.collection(mappedId)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.resume(
                                task.result.documents.map {
                                val word = it.toObject(CategoryItem::class.java)
                                word
                            })
                        } else {
                            emitter.resumeWithException(
                                task.exception ?: java.lang.Exception()
                            )
                        }
                    }
            }
        }
        return list.filterNotNull()
    }
}