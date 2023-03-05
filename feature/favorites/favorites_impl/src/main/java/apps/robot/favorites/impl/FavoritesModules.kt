package apps.robot.favorites.impl

import apps.robot.favorites.impl.base.favoritesBaseModule
import apps.robot.favorites.impl.list.favoritesListModule

fun favoritesModules() =
    listOf(
        favoritesBaseModule(),
        favoritesFeatureModule(),
        favoritesListModule()
    )