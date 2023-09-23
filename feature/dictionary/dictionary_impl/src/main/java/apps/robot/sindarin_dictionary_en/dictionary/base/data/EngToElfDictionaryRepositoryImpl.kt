package apps.robot.sindarin_dictionary_en.dictionary.base.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.EngToElfDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.EngToElfDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordDomainMapper
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordEngToElfEntityMapper
import apps.robot.sindarin_dictionary_en.dictionary.list.data.paging.DictionaryPagingSource
import apps.robot.sindarin_dictionary_en.dictionary.list.data.paging.DictionaryPagingSource.Companion.DICTIONARY_PAGE_SIZE
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class EngToElfDictionaryRepositoryImpl(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val dao: EngToElfDao,
    private val mapper: WordDomainMapper,
    private val engToElfEntityMapper: WordEngToElfEntityMapper,
    private val dictionaryPagingSource: DictionaryPagingSource<EngToElfWordEntity>
) : EngToElfDictionaryRepository {

    override suspend fun loadWords() {
        val words = runCatching {
            withContext(dispatchers.network) {
                suspendCoroutine<List<EngToElfWordEntity?>> { emitter ->
                    db.collection(ENG_TO_ELF_WORDS)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Timber.d("EngToElfDictionaryRepository: success ${task.result?.documents?.size}")
                                emitter.resume(
                                    task.result?.documents?.map {
                                        val word = it.toObject(EngToElfWordEntity::class.java)
                                        word?.id = it.id
                                        word
                                    } ?: emptyList()
                                )
                            } else {
                                emitter.resumeWithException(
                                    task.exception ?: java.lang.Exception()
                                )
                            }
                        }
                }
            }
        }.onFailure {
            Timber.d("Error while fetching data $it")
        }.getOrNull()

        words?.filterNotNull()?.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.word })
            ?.let { dao.insertAll(it) }
    }

    override fun getPagedWordsAsFlow(keyword: String?): Flow<PagingData<Word>> {
        return Pager(
            config = PagingConfig(
                pageSize = DICTIONARY_PAGE_SIZE
            ),
            pagingSourceFactory = {
                dictionaryPagingSource
            }
        ).flow.map { pagingData ->
            if (keyword != null) {
                pagingData.filter { it.word.startsWith(keyword) }.map {
                    mapper.map(it)
                }
            } else {
                pagingData.map {
                    mapper.map(it)
                }
            }
        }
    }

    override suspend fun getAllWords(): List<Word> {
        return dao.getAllWords().map(mapper::map)
    }

    override suspend fun getWordById(id: String): Word {
        return mapper.map(dao.getWordById(id))
    }

    override suspend fun updateWord(word: Word) {
        dao.update(engToElfEntityMapper.map(word))
    }

    override fun getFavoriteWordsAsFlow(): Flow<List<Word>> {
        return dao.getFavoriteWordsAsFlow().map { it.map(mapper::map) }
    }

    override fun getWordsSize(): Int {
        return dao.getWordsSize()
    }

    private companion object {
        const val ENG_TO_ELF_WORDS = "eng_to_elf_words"
    }
}