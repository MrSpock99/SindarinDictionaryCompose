package apps.robot.phrasebook.impl.base.data

import apps.robot.phrasebook.api.CategoryItem
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class FirebasePhrasebookDataSource(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val fallback: LocalPhrasebookDataSource,
) : PhrasebookDataSource {
    override suspend fun loadItems(): List<CategoryItem> {
        val remoteItems = mutableListOf<CategoryItem>()
        for (categoryId in CATEGORY_IDS) {
            val items = runCatching {
                withContext(dispatchers.network) {
                    suspendCoroutine { continuation ->
                        db.collection(categoryId)
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    continuation.resume(
                                        task.result?.documents?.mapNotNull { document ->
                                            document.toObject(CategoryItem::class.java)?.apply {
                                                id = document.id
                                                category = categoryId
                                            }
                                        } ?: emptyList()
                                    )
                                } else {
                                    continuation.resumeWithException(task.exception ?: Exception())
                                }
                            }
                    }
                }
            }.onFailure {
                Timber.d("Error while fetching phrasebook category $categoryId: $it")
            }.getOrNull()

            if (items == null) return fallback.loadItems()
            remoteItems += items
        }
        return remoteItems
    }

    private companion object {
        val CATEGORY_IDS = listOf(
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
            "colors",
        )
    }
}
