package apps.robot.favorites.impl.list.domain

import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.impl.list.presentation.model.FavoriteUiModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesyGetFavoritesAsFlowUseCase(
    private val dao: FavoritesDao
) {
    operator fun invoke(keyWord: String? = null): Flow<List<FavoriteUiModel>> {
        return dao.getFavoriteWordsAsFlow().map {
            if (keyWord != null) {
                it.filter { it.text.startsWith(keyWord) }.map {
                    FavoriteUiModel(it.id, UiText.DynamicString(it.text), UiText.DynamicString(it.translation))
                }
            } else {
                it.map { FavoriteUiModel(it.id, UiText.DynamicString(it.text), UiText.DynamicString(it.translation)) }
            }
        }
    }
}