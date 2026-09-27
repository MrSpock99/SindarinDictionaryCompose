package apps.robot.sindarin_dictionary_en.dictionary.base.data

import android.content.res.Resources
import apps.robot.dictionary.impl.R
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.Word
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

internal class LocalDictionaryDataSource(
    private val resources: Resources,
) : DictionaryDataSource {

    override suspend fun loadWords(dictionaryMode: DictionaryMode): List<Word> {
        val json = if (dictionaryMode == DictionaryMode.ELVISH_TO_ENGLISH) {
            resources.openRawResource(R.raw.elf_to_eng)
        } else {
            resources.openRawResource(R.raw.eng_to_elf)
        }.bufferedReader().use { it.readText() }

        val listType: Type = object : TypeToken<ArrayList<Word>>() {}.type
        return Gson().fromJson(json, listType)
    }
}
