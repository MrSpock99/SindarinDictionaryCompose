package apps.robot.sindarin_dictionary_en.dictionary.base.data

import android.content.res.Resources
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import apps.robot.dictionary.impl.R
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class EngToElfDictionaryRepositoryImpl(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val dao: EngToElfDao,
    private val mapper: WordDomainMapper,
    private val engToElfEntityMapper: WordEngToElfEntityMapper,
    private val dictionaryPagingSource: DictionaryPagingSource<EngToElfWordEntity>,
    private val resources: Resources,
) : EngToElfDictionaryRepository {

    override suspend fun loadWords() {
        val loadFromRemote = false

        val words = if (loadFromRemote) {
            loadWordsFromRemote()
        } else {
            loadWordsFromCache()
        }

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

    private suspend fun loadWordsFromRemote(): List<EngToElfWordEntity?>? {
        return runCatching {
            withContext(dispatchers.network) {
                suspendCoroutine { emitter ->
                    db.collection(ENG_TO_ELF_WORDS)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                emitter.resume(
                                    task.result?.documents?.map {
                                        val word = it.toObject(EngToElfWordEntity::class.java)
                                        word?.id = it.id
                                        word
                                    } ?: listOf()
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
    }

    private fun loadWordsFromCache(): List<EngToElfWordEntity?> {
        val json = resources.openRawResource(R.raw.eng_to_elf)
            .bufferedReader().use { it.readText() }
        val listType: Type = object : TypeToken<ArrayList<EngToElfWordEntity?>?>() {}.type
        return Gson().fromJson<List<EngToElfWordEntity>?>(json, listType)
    }

    private companion object {
        const val ENG_TO_ELF_WORDS = "eng_to_elf_words"
    }
}