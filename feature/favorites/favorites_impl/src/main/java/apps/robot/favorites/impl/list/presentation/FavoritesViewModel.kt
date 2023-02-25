package apps.robot.favorites.impl.list.presentation

import androidx.lifecycle.viewModelScope
import apps.robot.favorites.impl.list.domain.DictionaryGetFavoritesAsFlowUseCase
import apps.robot.favorites.impl.list.presentation.model.FavoritesListState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.Loading
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.SearchWidgetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus

internal class FavoritesViewModel(
    private val getFavoritesAsFlow: DictionaryGetFavoritesAsFlowUseCase,
    private val dispatchers: AppDispatchers
) : BaseViewModel() {

    val state = MutableStateFlow(FavoritesListState(uiState = Loading))

    init {
        getFavoritesAsFlow()
            .onEach { list ->
                state.update {
                    it.copy(favoritesList = list)
                }
            }.launchIn(viewModelScope + dispatchers.computing)
    }

    fun onSearchToggle() {
        state.update {
            it.copy(
                searchWidgetState = if (state.value.searchWidgetState == SearchWidgetState.OPENED) {
                    SearchWidgetState.CLOSED
                } else {
                    SearchWidgetState.OPENED
                }
            )
        }
    }

    fun onSearchTextChange(searchText: String) {
        state.value.searchText.tryEmit(searchText)
    }
}