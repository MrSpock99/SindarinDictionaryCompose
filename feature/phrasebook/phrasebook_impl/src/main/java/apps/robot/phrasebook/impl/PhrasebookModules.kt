package apps.robot.phrasebook.impl

import apps.robot.phrasebook.impl.base.phrasebookBaseModule
import apps.robot.phrasebook.impl.categories.phrasebookCategoriesModule

fun phrasebookModules() =
    listOf(
        phrasebookFeatureModule(),
        phrasebookCategoriesModule(),
        phrasebookBaseModule()
    )