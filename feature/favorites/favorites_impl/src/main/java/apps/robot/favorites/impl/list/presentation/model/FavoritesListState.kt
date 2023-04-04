package apps.robot.favorites.impl.list.presentation.model

import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.SearchWidgetState
import kotlinx.coroutines.flow.MutableStateFlow

internal data class FavoritesListState(
    val favoritesList: List<FavoriteUiModel> = emptyList(),
    val uiState: UiState,
    val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,
    val searchText: MutableStateFlow<String> = MutableStateFlow("")
)