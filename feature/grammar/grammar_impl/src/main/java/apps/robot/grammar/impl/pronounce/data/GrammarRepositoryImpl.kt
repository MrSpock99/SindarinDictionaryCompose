package apps.robot.grammar.impl.pronounce.data

import apps.robot.grammar.api.PronounceDao
import apps.robot.grammar.api.PronounceItem
import apps.robot.grammar.impl.pronounce.domain.GrammarRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GrammarRepositoryImpl(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val dao: PronounceDao
) : GrammarRepository {
    override suspend fun loadPronounceItems() {
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
        dao.addItems(pronounceItems.filterNotNull())
    }

    override suspend fun getPronunciationAsFlow(): Flow<List<PronounceItem>> {
        return dao.getItemsAsFlow()
    }
}