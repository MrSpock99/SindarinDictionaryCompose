package apps.robot.grammar.impl.pronounce.data

import apps.robot.grammar.impl.pronounce.domain.GrammarRepository
import apps.robot.grammar.impl.pronounce.domain.PronounceItem
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GrammarRepositoryImpl(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
) : GrammarRepository {

    override suspend fun getPronunciation(): List<PronounceItem> {
        val pronounceItems = withContext(dispatchers.network) {
            suspendCoroutine<List<PronounceItem?>> { emitter ->
                db.collection("pronunciation")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.resume(
                                task.result?.documents?.map {
                                    val word = it.toObject(PronounceItem::class.java)
                                    word
                                } ?: emptyList()
                            )
                        } else {
                            emitter.resumeWithException(
                                task.exception ?: Exception()
                            )
                        }
                    }
            }
        }
        return pronounceItems.filterNotNull()
    }
}