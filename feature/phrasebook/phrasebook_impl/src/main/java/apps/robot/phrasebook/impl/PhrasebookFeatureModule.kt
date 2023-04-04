package apps.robot.phrasebook.impl

import apps.robot.phrasebook.api.PhrasebookFeatureApi
import apps.robot.phrasebook.impl.navigation.PhrasebookFeatureApiImpl
import org.koin.dsl.module

internal fun phrasebookFeatureModule() = module {
    factory<PhrasebookFeatureApi> { PhrasebookFeatureApiImpl() }
}