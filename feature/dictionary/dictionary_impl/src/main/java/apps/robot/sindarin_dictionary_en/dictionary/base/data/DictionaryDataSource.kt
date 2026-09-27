package apps.robot.sindarin_dictionary_en.dictionary.base.data

import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word

internal interface DictionaryDataSource {
    suspend fun loadWords(dictionaryMode: DictionaryMode): List<Word>
}
