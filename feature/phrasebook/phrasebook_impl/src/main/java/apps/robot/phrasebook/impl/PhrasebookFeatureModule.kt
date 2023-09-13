package apps.robot.phrasebook.impl

import apps.robot.phrasebook.api.PhrasebookFeatureApi
import apps.robot.phrasebook.impl.navigation.PhrasebookFeatureApiImpl
import apps.robot.phrasebook.impl.navigation.PhrasebookInternalFeature
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.processLifecycleScope
import org.koin.dsl.bind
import org.koin.dsl.module

internal fun phrasebookFeatureModule() = module {
    factory<PhrasebookFeatureApi> {
        PhrasebookFeatureApiImpl(
            phrasebookInternalFeature = get()
        )
    }

    factory { PhrasebookInternalFeature() }

    factory {
        PhrasebookInitializer(
            repository = get(),
            coroutineScope = processLifecycleScope,
            appDispatchers = get()
        )
    } bind BaseAppInitializer::class
}