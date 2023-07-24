package apps.robot.grammar.impl

import apps.robot.grammar.api.GrammarFeatureApi
import org.koin.dsl.module

internal fun grammarFeatureModule() = module {
    factory<GrammarFeatureApi> { GrammarFeatureApiImpl() }
}