package apps.robot.favorites.impl.list.domain

import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.api.domain.FavoriteModel
import apps.robot.favorites.api.domain.FavoritesUpdateFavoriteStatusUseCase

class FavoritesUpdateFavoriteStatusUseCaseImpl(
    private val dao: FavoritesDao
) : FavoritesUpdateFavoriteStatusUseCase {

    override suspend fun invoke(favoriteModel: FavoriteModel, isFavorite: Boolean) {
        if (isFavorite) {
            dao.add(favoriteModel)
        } else {
            dao.remove(favoriteModel)
        }
    }
}