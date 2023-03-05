package apps.robot.favorites.impl.base

import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.api.data.OldFavoritesDao
import apps.robot.favorites.api.domain.FavoriteModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseAppInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class OldToNewFavoritesInitializer(
    private val coroutineScope: CoroutineScope,
    private val oldFavoritesDao: OldFavoritesDao,
    private val favoritesDao: FavoritesDao
) : BaseAppInitializer {

    override fun onAppStartInit() {
        coroutineScope.launch {
            val oldFavoritesTable = oldFavoritesDao.getAll()
            if (oldFavoritesTable.isNotEmpty()) {
                val oldFavorites = oldFavoritesTable.map {
                    val text = it.text?.substringBefore("|").orEmpty()
                    val translation = it.text?.substringAfter("|").orEmpty()
                    FavoriteModel(
                        id = it.id.toString(),
                        text = text,
                        translation = translation
                    )
                }
                oldFavorites.forEach {
                    favoritesDao.add(it)
                }
                oldFavoritesDao.deleteAll()
            }
        }
    }
}