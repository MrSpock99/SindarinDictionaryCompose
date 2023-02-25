package apps.robot.favorites.api.domain

interface FavoritesGetFavoriteByIdUseCase {
    suspend operator fun invoke(id: String): FavoriteModel?
}