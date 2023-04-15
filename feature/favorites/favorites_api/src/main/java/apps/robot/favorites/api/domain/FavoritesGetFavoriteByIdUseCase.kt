package apps.robot.favorites.api.domain

interface FavoritesGetFavoriteByIdUseCase {
    suspend operator fun invoke(text: String, translation: String): FavoriteModel?
}