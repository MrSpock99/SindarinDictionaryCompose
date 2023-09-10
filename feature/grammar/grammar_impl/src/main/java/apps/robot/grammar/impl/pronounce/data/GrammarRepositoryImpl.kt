package apps.robot.grammar.impl.pronounce.data

import apps.robot.grammar.api.PronounceDao
import apps.robot.grammar.api.PronounceItem
import apps.robot.grammar.impl.plural.domain.PluralItem
import apps.robot.grammar.impl.pronounce.domain.GrammarRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.UUID
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

    override suspend fun getPronounceItemSize(): Int {
        return dao.getItemsSize()
    }

    override suspend fun loadPluralsItems() {
        // nothing yet
    }

    override fun getPluralItemsAsFlow(): Flow<List<PluralItem>> {
        val pluralData = mutableListOf<PluralItem>()

        pluralData.add(PluralItem(UUID.randomUUID().toString(),"A", "e", "ai (арх. ei*)", "Adan – Edain\nAras - Erais"))
        pluralData.add(PluralItem(UUID.randomUUID().toString(),"E", "e", "i", "Edhel - Edhil"))
        pluralData.add(PluralItem(UUID.randomUUID().toString(),"I", "i ", "i", "Ithil - Ithil"))
        pluralData.add(PluralItem(UUID.randomUUID().toString(),"O", "e", "y", "Orod – Eryd\nBrog - Bryg"))
        pluralData.add(PluralItem(UUID.randomUUID().toString(),"U", "y", "y\nui (long)", "Ungol – Yngyl\nDûr - Duir"))
        pluralData.add(PluralItem(UUID.randomUUID().toString(),"Y", "y", "y", "Ylf - Ylf"))
        pluralData.add(PluralItem(UUID.randomUUID().toString(),"io", "y", "y", "Thalion - Thelyn"))
        pluralData.add(PluralItem(UUID.randomUUID().toString(),"au", "oe", "oe", "Draug - Droeg"))
        pluralData.add(PluralItem(UUID.randomUUID().toString(),"ie", "ie", "i", "Tiriel - Tiril"))

        return flow {
            emit(pluralData)
        }
    }
}