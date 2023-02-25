package apps.robot.sindarin_dictionary_en.dictionary.base.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.ElfToEngDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.ElfToEngDictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordDomainMapper
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordElfToEngEntityMapper
import apps.robot.sindarin_dictionary_en.dictionary.list.data.paging.DictionaryPagingSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class ElfToEngDictionaryRepositoryImpl(
    private val db: FirebaseFirestore,
    private val dispatchers: AppDispatchers,
    private val dao: ElfToEngDao,
    private val mapper: WordDomainMapper,
    private val elfToEngEntityMapper: WordElfToEngEntityMapper,
    private val dictionaryPagingSource: DictionaryPagingSource<ElfToEngWordEntity>
) : ElfToEngDictionaryRepository {

    override suspend fun loadWords() {
        val words = withContext(dispatchers.network) {
            suspendCoroutine<List<ElfToEngWordEntity?>> { emitter ->
                db.collection(ELF_TO_ENG_WORDS)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.resume(
                                task.result.documents.map {
                                    val word = it.toObject(ElfToEngWordEntity::class.java)
                                    word?.id = it.id
                                    word
                                }
                            )
                        } else {
                            emitter.resumeWithException(
                                task.exception ?: java.lang.Exception()
                            )
                        }
                    }
            }
        }
        val favorites = dao.getFavoriteWords()
        favorites.forEach { favoriteWord ->
            val remoteWord = words.find { it?.id == favoriteWord.id }
            remoteWord?.isFavorite = favoriteWord.isFavorite
        }
        dao.insertAll(words.filterNotNull().sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.word }))
    }

    override fun getPagedWordsAsFlow(keyword: String?): Flow<PagingData<Word>> {
        return Pager(
            config = PagingConfig(
                pageSize = DictionaryPagingSource.DICTIONARY_PAGE_SIZE
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
        dao.update(elfToEngEntityMapper.map(word))
    }

    override fun getFavoriteWordsAsFlow(): Flow<List<Word>> {
        return dao.getFavoriteWordsAsFlow().map { it.map(mapper::map) }
    }

    private companion object {
        const val ELF_TO_ENG_WORDS = "elf_to_eng_words"
    }
}