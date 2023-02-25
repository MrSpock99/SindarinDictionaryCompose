package apps.robot.favorites.impl

import apps.robot.favorites.api.FavoritesFeatureApi
import apps.robot.favorites.impl.navigation.FavoritesFeatureApiImpl
import org.koin.dsl.module

internal fun favoritesFeatureModule() = module {
    factory<FavoritesFeatureApi> { FavoritesFeatureApiImpl() }
}