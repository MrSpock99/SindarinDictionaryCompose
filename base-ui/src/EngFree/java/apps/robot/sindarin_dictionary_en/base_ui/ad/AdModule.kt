package apps.robot.sindarin_dictionary_en.base_ui.ad

import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

fun adModule() = module {
    factory { AdInitializerImpl(androidApplication()) } bind BaseAppInitializer::class
}