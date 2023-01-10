package apps.robot.sindarin_dictionary_en.dictionary.list.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import apps.robot.sindarin_dictionary_en.dictionary.api.data.local.DictionaryDao

internal class DictionaryPagingSource<V : Any>(
    private val dictionaryDao: DictionaryDao<V>
) : PagingSource<Int, V>() {

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val pageIndex = params.key ?: INITIAL_PAGE_INDEX
        val words = dictionaryDao.getPagedWords(params.loadSize, pageIndex * params.loadSize)
        return LoadResult.Page(
            data = words,
            prevKey = if (pageIndex == INITIAL_PAGE_INDEX) null else pageIndex,
            nextKey = if (words.isEmpty()) null else pageIndex + (params.loadSize / DICTIONARY_PAGE_SIZE)
        )
    }

    companion object {
        const val DICTIONARY_PAGE_SIZE = 30
        private const val INITIAL_PAGE_INDEX = 0
    }
}