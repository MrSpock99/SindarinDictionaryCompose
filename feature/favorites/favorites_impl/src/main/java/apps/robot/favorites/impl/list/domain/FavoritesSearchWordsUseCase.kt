package apps.robot.favorites.impl.list.domain

import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.impl.list.presentation.model.FavoriteUiModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText

class FavoritesSearchWordsUseCase(
    private val dao: FavoritesDao
) {
    suspend operator fun invoke(keyword: String): List<FavoriteUiModel> {
        return dao.getAll()
            .filterNotNull()
            .filter {
                it.text.startsWith(keyword)
            }
            .map {
                FavoriteUiModel(it.id, UiText.DynamicString(it.text), UiText.DynamicString(it.translation))
            }
    }
}