package apps.robot.favorites.impl.list.presentation

import androidx.lifecycle.viewModelScope
import apps.robot.favorites.impl.list.domain.FavoritesSearchWordsUseCase
import apps.robot.favorites.impl.list.domain.FavoritesyGetFavoritesAsFlowUseCase
import apps.robot.favorites.impl.list.presentation.model.FavoritesListState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.Loading
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus

internal class FavoritesViewModel(
    getFavoritesAsFlow: FavoritesyGetFavoritesAsFlowUseCase,
    private val dispatchers: AppDispatchers,
    private val searchWords: FavoritesSearchWordsUseCase
) : BaseViewModel() {

    val state = MutableStateFlow(FavoritesListState(uiState = Loading))

    init {
        getFavoritesAsFlow()
            .onEach { list ->
                state.update {
                    it.copy(favoritesList = list)
                }
            }.launchIn(viewModelScope + dispatchers.computing)
        subscribeToSearch()
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

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun subscribeToSearch() {
        state.value.searchText.debounce(SEARCH_DEBOUNCE)
            .mapLatest {
                state.value = state.value.copy(
                    favoritesList = searchWords(keyword = it)
                )
            }.launchIn(viewModelScope + dispatchers.computing)
    }

    private companion object {
        const val SEARCH_DEBOUNCE = 300L
    }
}