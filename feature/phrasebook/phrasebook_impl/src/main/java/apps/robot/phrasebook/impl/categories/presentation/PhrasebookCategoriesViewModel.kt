package apps.robot.phrasebook.impl.categories.presentation

import android.content.Context
import androidx.lifecycle.viewModelScope
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.Loading
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.SearchWidgetState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus

class PhrasebookCategoriesViewModel(
    private val repository: PhrasebookRepository,
    private val dispatchers: AppDispatchers,
    context: Context
) : BaseViewModel() {
    val state = MutableStateFlow(PhrasebookListState(uiState = Loading))

    init {
        loadCategories()
        subscribeToSearch(context)
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
    private fun subscribeToSearch(context: Context) {
        state.value.searchText.debounce(SEARCH_DEBOUNCE)
            .mapLatest { searchQuery ->
                if (searchQuery.isNotEmpty()) {
                    state.value = state.value.copy(
                        categoriesList = state.value.categoriesList.filter {
                            it.text.asString(context).lowercase().startsWith(searchQuery.lowercase())
                        }
                    )
                } else {
                    loadCategories()
                }
            }.launchIn(viewModelScope + dispatchers.computing)
    }

    private fun loadCategories() {
        state.value = state.value.copy(
            categoriesList = repository.getPhrasebookCategories().map {
                PhrasebookCategoryUiModel(UiText.DynamicString(it))
            }
        )
    }

    private companion object {
        const val SEARCH_DEBOUNCE = 300L
    }

    data class PhrasebookListState(
        val categoriesList: List<PhrasebookCategoryUiModel> = emptyList(),
        val uiState: UiState,
        val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,
        val searchText: MutableStateFlow<String> = MutableStateFlow(""),
    )
}