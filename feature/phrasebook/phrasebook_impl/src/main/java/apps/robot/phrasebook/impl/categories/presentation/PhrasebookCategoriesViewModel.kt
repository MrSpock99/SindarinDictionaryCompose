package apps.robot.phrasebook.impl.categories.presentation

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import apps.robot.phrasebook.impl.BuildConfig
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import apps.robot.phrasebook.impl.navigation.PhrasebookInternalFeature
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.Searchable
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import apps.robot.sindarin_dictionary_en.base_ui.presentation.isFree
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import org.koin.androidx.compose.get

class PhrasebookCategoriesViewModel(
    private val repository: PhrasebookRepository,
    private val dispatchers: AppDispatchers,
    private val phrasebookInternalFeature: PhrasebookInternalFeature,
    context: Context
) : BaseViewModel(), Searchable {

    val state = MutableStateFlow(PhrasebookListState(uiState = UiState.Loading))

    init {
        loadCategories()
        subscribeToSearch(context)
    }

    override fun onSearchToggle() {
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

    override fun onSearchTextChange(searchText: String) {
        state.value.searchText.tryEmit(searchText)
    }

    fun onPhrasebookCategoryClick(
        item: PhrasebookCategoryUiModel,
        navigator: NavController,
        context: Context
    ) {
        val proCategories = repository.getPhrasebookProCategories()

        if (isFree() && !proCategories.contains(item.text.asString(context))) {
            navigator.navigate(
                phrasebookInternalFeature.phrasebookCategoryScreen(
                    categoryName = item.text.asString(context)
                )
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun subscribeToSearch(context: Context) {
        state.value.searchText.debounce(SEARCH_DEBOUNCE)
            .mapLatest { searchQuery ->
                if (searchQuery.isNotEmpty()) {
                    state.value = state.value.copy(
                        categoriesList = state.value.categoriesList.filter {
                            it.text.asString(context).lowercase()
                                .startsWith(searchQuery.lowercase())
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

    data class PhrasebookListState(
        override val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,
        override val searchText: MutableStateFlow<String> = MutableStateFlow(""),
        val categoriesList: List<PhrasebookCategoryUiModel> = emptyList(),
        val uiState: UiState,
    ) : Searchable.SearchableState

    private companion object {
        const val SEARCH_DEBOUNCE = 300L
    }
}