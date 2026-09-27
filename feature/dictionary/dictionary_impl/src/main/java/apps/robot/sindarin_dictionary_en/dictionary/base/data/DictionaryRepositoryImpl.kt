package apps.robot.sindarin_dictionary_en.dictionary.base.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.DictionaryDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.ElfToEngDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.EngToElfDao
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.ElfToEngWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.model.EngToElfWordEntity
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordDomainMapper
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordEngToElfEntityMapper
import apps.robot.sindarin_dictionary_en.dictionary.base.data.mappers.WordElfToEngEntityMapper
import apps.robot.sindarin_dictionary_en.dictionary.list.data.paging.DictionaryPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DictionaryRepositoryImpl(
    private val dataSource: DictionaryDataSource,
    private val elfToEngDao: ElfToEngDao,
    private val engToElfDao: EngToElfDao,
    private val mapper: WordDomainMapper,
    private val engToElfEntityMapper: WordEngToElfEntityMapper,
    private val elfToEngEntityMapper: WordElfToEngEntityMapper,
    private val elfToEngPagingSource: DictionaryPagingSource<ElfToEngWordEntity>,
    private val engToElfPagingSource: DictionaryPagingSource<EngToElfWordEntity>,
) : DictionaryRepository {

    override suspend fun loadWords(dictionaryMode: DictionaryMode) {
        if (getWordsSize(dictionaryMode) > 0) return

        val words = dataSource.loadWords(dictionaryMode)
        val sortedWords = words.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.word })

        if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
            elfToEngDao.insertAll(sortedWords.map(elfToEngEntityMapper::map))
        } else {
            engToElfDao.insertAll(sortedWords.map(engToElfEntityMapper::map))
        }
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

    override fun getWordsSize(dictionaryMode: DictionaryMode): Int {
        return if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
            elfToEngDao.getWordsSize()
        } else {
            engToElfDao.getWordsSize()
        }
    }

    private fun getDao(dictionaryMode: DictionaryMode): DictionaryDao<out Any> {
        return if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
            elfToEngDao
        } else {
            engToElfDao
        }
    }

}
