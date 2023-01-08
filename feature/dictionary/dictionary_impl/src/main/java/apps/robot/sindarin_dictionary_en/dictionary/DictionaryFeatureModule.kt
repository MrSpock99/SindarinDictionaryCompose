package apps.robot.sindarin_dictionary_en.dictionary

import apps.robot.sindarin_dictionary_en.base_ui.presentation.navigation.FeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.navigation.DictionaryFeatureApiImpl
import apps.robot.sindarin_dictionary_en.dictionary.navigation.DictionaryInternalFeature
import org.koin.dsl.module

internal fun dictionaryFeatureModule() = module {
    factory<DictionaryFeatureApi> { DictionaryFeatureApiImpl(get()) }
    factory<FeatureApi> { DictionaryInternalFeature() }
}