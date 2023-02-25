package apps.robot.favorites.impl

import apps.robot.favorites.impl.list.favoritesListModule

fun favoritesModules() =
    listOf(
        favoritesFeatureModule(),
        favoritesListModule()
    )