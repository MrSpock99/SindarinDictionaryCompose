package apps.robot.favorites.impl.list.domain

import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.api.domain.FavoriteModel
import apps.robot.favorites.api.domain.FavoritesUpdateFavoriteStatusUseCase

class FavoritesUpdateFavoriteStatusUseCaseImpl(
    private val favoritesDao: FavoritesDao
) : FavoritesUpdateFavoriteStatusUseCase {

    override suspend fun invoke(favoriteModel: FavoriteModel, isFavorite: Boolean) {
        if (isFavorite) {
            favoritesDao.add(favoriteModel)
        } else {
            favoritesDao.remove(favoriteModel)
        }
    }
}