package apps.robot.grammar.impl.pronounce.data

import apps.robot.grammar.api.PronounceItem
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class FirebasePronounceDataSource(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val fallback: LocalPronounceDataSource,
) : PronounceDataSource {
    override suspend fun loadItems(): List<PronounceItem> = runCatching {
        withContext(dispatchers.network) {
            suspendCoroutine { continuation ->
                db.collection(COLLECTION)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(
                                task.result?.documents?.mapNotNull {
                                    it.toObject(PronounceItem::class.java)
                                } ?: emptyList()
                            )
                        } else {
                            continuation.resumeWithException(task.exception ?: Exception())
                        }
                    }
            }
        }
    }.onFailure {
        Timber.d("Error while fetching pronunciation data $it")
    }.getOrNull() ?: fallback.loadItems()

    private companion object {
        const val COLLECTION = "pronunciation"
    }
}
