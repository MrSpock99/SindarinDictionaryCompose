package apps.robot.phrasebook.impl.base.data

import android.content.res.Resources
import apps.robot.phrasebook.api.CategoryItem
import apps.robot.phrasebook.impl.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class LocalPhrasebookDataSource(
    private val resources: Resources,
) : PhrasebookDataSource {
    override suspend fun loadItems(): List<CategoryItem> {
        val json = resources.openRawResource(R.raw.phrasebook)
            .bufferedReader()
            .use { it.readText() }
        val listType = object : TypeToken<ArrayList<CategoryItem>>() {}.type
        return Gson().fromJson(json, listType)
    }
}
