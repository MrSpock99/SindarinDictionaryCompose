package apps.robot.phrasebook.impl.base.data

import android.content.res.Resources
import apps.robot.phrasebook.impl.R
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository

class PhrasebookRepositoryImpl(
    private val resources: Resources
) : PhrasebookRepository {

    override fun getPhrasebookCategories(): List<String> {
        return resources.getStringArray(R.array.phrasebook_categories).toList()
    }
}