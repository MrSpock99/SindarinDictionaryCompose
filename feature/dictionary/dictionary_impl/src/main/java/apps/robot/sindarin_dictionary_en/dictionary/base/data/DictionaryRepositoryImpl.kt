package apps.robot.sindarin_dictionary_en.dictionary.base.data

import android.content.res.Resources
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import apps.robot.dictionary.impl.R
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.DictionaryDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.ElfToEngDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.EngToElfDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordDomainMapper
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordElfToEngEntityMapper
import apps.robot.sindarin_dictionary_en.dictionary.list.data.paging.DictionaryPagingSource
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

internal class DictionaryRepositoryImpl(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val elfToEngDao: ElfToEngDao,
    private val engToElfDao: EngToElfDao,
    private val mapper: WordDomainMapper,
    private val elfToEngEntityMapper: WordElfToEngEntityMapper,
    private val elfToEngPagingSource: DictionaryPagingSource<ElfToEngWordEntity>,
    private val engToElfPagingSource: DictionaryPagingSource<EngToElfWordEntity>,
    private val resources: Resources,
) : DictionaryRepository {

    override suspend fun loadWords(dictionaryMode: DictionaryMode) {
        val loadFromRemote = false

        val words = if (loadFromRemote) {
            loadWordsFromRemote(dictionaryMode)
        } else {
            loadWordsFromCache(dictionaryMode)
        }

        words?.filterNotNull()?.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.word })
            ?.let { elfToEngDao.insertAll(it) }
    }

    override fun getPagedWordsAsFlow(dictionaryMode: DictionaryMode, keyword: String?): Flow<PagingData<Word>> {
        return Pager(
            config = PagingConfig(
                pageSize = DictionaryPagingSource.DICTIONARY_PAGE_SIZE
            ),
            pagingSourceFactory = {
                if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
                    elfToEngPagingSource
                } else {
                    engToElfPagingSource
                }
            }
        ).flow.map { pagingData ->
            if (keyword != null) {
                pagingData.filter {
                    it.word.startsWith(keyword)
                }.map {
                    mapper.map(it)
                }
            } else {
                pagingData.map {
                    mapper.map(it)
                }
            }
        }
    }

    override suspend fun getAllWords(dictionaryMode: DictionaryMode): List<Word> {
        val dao = getDao(dictionaryMode)
        return dao.getAllWords().map(mapper::map)
    }

    override suspend fun getWordById(dictionaryMode: DictionaryMode, id: String): Word {
        return mapper.map(elfToEngDao.getWordById(id))
    }

    override suspend fun updateWord(dictionaryMode: DictionaryMode, word: Word) {
        elfToEngDao.update(elfToEngEntityMapper.map(word))
    }

    override fun getFavoriteWordsAsFlow(dictionaryMode: DictionaryMode): Flow<List<Word>> {
        return elfToEngDao.getFavoriteWordsAsFlow().map { it.map(mapper::map) }
    }

    override fun getWordsSize(): Int {
        return engToElfDao.getWordsSize()
    }

    private suspend fun loadWordsFromRemote(dictionaryMode: DictionaryMode): List<ElfToEngWordEntity?>? {

        return runCatching {
            val dbName = if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
                ELF_TO_ENG_WORDS
            } else {
                ENG_TO_ELF_WORDS
            }
            withContext(dispatchers.network) {
                suspendCoroutine { emitter ->
                    db.collection(dbName)
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                emitter.resume(
                                    task.result?.documents?.map {
                                        val word = it.toObject(ElfToEngWordEntity::class.java)
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

    private fun loadWordsFromCache(dictionaryMode: DictionaryMode): List<ElfToEngWordEntity?> {
        val json = if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
            resources.openRawResource(R.raw.elf_to_eng)
        } else {
            resources.openRawResource(R.raw.eng_to_elf)
        }.bufferedReader().use { it.readText() }

        val listType: Type = object : TypeToken<ArrayList<ElfToEngWordEntity?>?>() {}.type
        return Gson().fromJson<List<ElfToEngWordEntity>?>(json, listType)
    }

    private fun getDao(dictionaryMode: DictionaryMode): DictionaryDao<out Any> {
        return if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
            engToElfDao
        } else {
            elfToEngDao
        }
    }

    private companion object {
        const val ELF_TO_ENG_WORDS = "elf_to_eng_words"
        const val ENG_TO_ELF_WORDS = "eng_to_elf_words"
    }
}