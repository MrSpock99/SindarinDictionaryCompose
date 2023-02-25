package apps.robot.favorites.impl.list.domain

import apps.robot.favorites.api.data.FavoritesDao
import apps.robot.favorites.impl.list.presentation.model.FavoriteUiModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DictionaryGetFavoritesAsFlowUseCase(
    private val dao: FavoritesDao
) {
    operator fun invoke(): Flow<List<FavoriteUiModel>> {
        return dao.getFavoriteWordsAsFlow().map {
            it.map {
                FavoriteUiModel(it.id, UiText.DynamicString(it.text), UiText.DynamicString(it.translation))
            }
        }
    }
}