package apps.robot.favorites.impl.base

import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.processLifecycleScope
import org.koin.dsl.module

fun favoritesBaseModule() = module {
    factory<BaseAppInitializer> {
        OldToNewFavoritesInitializer(
            coroutineScope = processLifecycleScope,
            oldFavoritesDao = get(),
            favoritesDao = get()
        )
    }
}