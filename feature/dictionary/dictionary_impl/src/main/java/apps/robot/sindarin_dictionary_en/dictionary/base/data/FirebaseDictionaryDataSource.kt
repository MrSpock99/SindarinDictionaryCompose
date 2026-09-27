package apps.robot.sindarin_dictionary_en.dictionary.base.data

import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordDomainMapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class FirebaseDictionaryDataSource(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val mapper: WordDomainMapper,
    private val fallback: LocalDictionaryDataSource,
) : DictionaryDataSource {

    override suspend fun loadWords(dictionaryMode: DictionaryMode): List<Word> = runCatching {
        val collection = if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
            ELF_TO_ENG_WORDS
        } else {
            ENG_TO_ELF_WORDS
        }

        withContext(dispatchers.network) {
            suspendCoroutine { continuation ->
                db.collection(collection)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(
                                task.result?.documents?.mapNotNull { document ->
                                    if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
                                        document.toObject(ElfToEngWordEntity::class.java)
                                            ?.apply { id = document.id }
                                            ?.let(mapper::map)
                                    } else {
                                        document.toObject(EngToElfWordEntity::class.java)
                                            ?.apply { id = document.id }
                                            ?.let(mapper::map)
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
        Timber.d("Error while fetching data $it")
    }.getOrNull() ?: fallback.loadWords(dictionaryMode)

    private companion object {
        const val ELF_TO_ENG_WORDS = "elf_to_eng_words"
        const val ENG_TO_ELF_WORDS = "eng_to_elf_words"
    }
}
