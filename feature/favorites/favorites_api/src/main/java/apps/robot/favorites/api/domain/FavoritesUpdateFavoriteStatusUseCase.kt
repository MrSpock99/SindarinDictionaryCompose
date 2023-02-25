package apps.robot.favorites.api.domain

interface FavoritesUpdateFavoriteStatusUseCase {
    suspend operator fun invoke(favoriteModel: FavoriteModel, isFavorite: Boolean)
}