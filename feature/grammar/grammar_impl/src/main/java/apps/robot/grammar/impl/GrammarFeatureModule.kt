package apps.robot.grammar.impl

import apps.robot.grammar.api.GrammarFeatureApi
import apps.robot.grammar.impl.plural.presentation.PluralViewModel
import apps.robot.grammar.impl.pronounce.data.GrammarRepositoryImpl
import apps.robot.grammar.impl.pronounce.domain.GrammarRepository
import apps.robot.grammar.impl.pronounce.domain.PronounceInitializer
import apps.robot.grammar.impl.pronounce.presentation.PronounceViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.processLifecycleScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

internal fun grammarFeatureModule() = module {
    factory<GrammarFeatureApi> { GrammarFeatureApiImpl(get()) }
    factory { GrammarInternalFeature() }
    factory<GrammarRepository> { GrammarRepositoryImpl(get(), get(), get()) }
    factory {
        PronounceInitializer(
            coroutineScope = processLifecycleScope,
            appDispatchers = get(),
            grammarRepository = get()
        )
    } bind BaseAppInitializer::class

    viewModel {
        PronounceViewModel(
            get()
        )
    }

    viewModel {
        PluralViewModel(
            get()
        )
    }
}