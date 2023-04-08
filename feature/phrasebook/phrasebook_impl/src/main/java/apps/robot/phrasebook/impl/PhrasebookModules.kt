package apps.robot.phrasebook.impl

import apps.robot.phrasebook.impl.base.phrasebookBaseModule
import apps.robot.phrasebook.impl.categories.phrasebookCategoryModule
import apps.robot.phrasebook.impl.category.phrasebookCategoriesModule

fun phrasebookModules() =
    listOf(
        phrasebookFeatureModule(),
        phrasebookCategoriesModule(),
        phrasebookCategoryModule(),
        phrasebookBaseModule()
    )