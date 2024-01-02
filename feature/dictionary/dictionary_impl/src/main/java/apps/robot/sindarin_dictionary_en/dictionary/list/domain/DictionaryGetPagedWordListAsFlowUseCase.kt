package apps.robot.sindarin_dictionary_en.dictionary.list.domain

import androidx.paging.PagingData
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryRepository
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import kotlinx.coroutines.flow.Flow

internal class DictionaryGetPagedWordListAsFlowUseCase(
    private val dictionaryRepository: DictionaryRepository,
) {

    operator fun invoke(dictionaryMode: DictionaryMode): Flow<PagingData<Word>> {
        return dictionaryRepository.getPagedWordsAsFlow(dictionaryMode)
    }
}