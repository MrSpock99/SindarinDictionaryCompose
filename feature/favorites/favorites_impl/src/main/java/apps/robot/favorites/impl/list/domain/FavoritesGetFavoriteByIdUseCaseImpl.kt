package apps.robot.favorites.impl.list.domain

import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.api.domain.FavoriteModel
import apps.robot.favorites.api.domain.FavoritesGetFavoriteByIdUseCase

class FavoritesGetFavoriteByIdUseCaseImpl(
    private val dao: FavoritesDao
) : FavoritesGetFavoriteByIdUseCase {

    override suspend fun invoke(text: String, translation: String): FavoriteModel? {
        return dao.get(text, translation)
    }
}