package apps.robot.grammar.impl

import apps.robot.grammar.api.GrammarFeatureApi
import apps.robot.grammar.impl.pronounce.data.GrammarRepositoryImpl
import apps.robot.grammar.impl.pronounce.domain.GrammarRepository
import apps.robot.grammar.impl.pronounce.presentation.PronounceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal fun grammarFeatureModule() = module {
    factory<GrammarFeatureApi> { GrammarFeatureApiImpl() }
    factory<GrammarRepository> { GrammarRepositoryImpl(get(), get(), get()) }

    viewModel {
        PronounceViewModel(
            get()
        )
    }
}