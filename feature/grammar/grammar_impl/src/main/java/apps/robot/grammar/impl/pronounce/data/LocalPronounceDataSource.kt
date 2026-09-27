package apps.robot.grammar.impl.pronounce.data

import android.content.res.Resources
import apps.robot.grammar.impl.R
import apps.robot.grammar.api.PronounceItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class LocalPronounceDataSource(
    private val resources: Resources,
) : PronounceDataSource {
    override suspend fun loadItems(): List<PronounceItem> {
        val json = resources.openRawResource(R.raw.pronunciation)
            .bufferedReader()
            .use { it.readText() }
        val listType = object : TypeToken<ArrayList<PronounceItem>>() {}.type
        return Gson().fromJson(json, listType)
    }
}
