package apps.robot.phrasebook.impl.category.presentation

import android.content.Context
import androidx.lifecycle.viewModelScope
import apps.robot.phrasebook.impl.base.domain.PhrasebookRepository
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.Searchable
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.coroutines.AppDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus

class PhrasebookCategoryViewModel(
    private val dispatchers: AppDispatchers,
    private val repository: PhrasebookRepository,
    context: Context
) : BaseViewModel(), Searchable {

    val state = MutableStateFlow(PhrasebookCategoryState(uiState = UiState.Loading))
    private var categoryName = ""

    init {
        subscribeToSearch(context)
    }

    fun onReceiveArgs(categoryName: String) {
        this.categoryName = categoryName
        state.value = state.value.copy(
            categoryName = UiText.DynamicString(categoryName)
        )
        loadItems()
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

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun subscribeToSearch(context: Context) {
        state.value.searchText.debounce(SEARCH_DEBOUNCE)
            .mapLatest { searchQuery ->
                if (searchQuery.isNotEmpty()) {
                    state.value = state.value.copy(
                        list = state.value.list.filter {
                            it.text.asString(context).lowercase()
                                .startsWith(searchQuery.lowercase())
                        }
                    )
                } else {
                    loadItems()
                }
            }.launchIn(viewModelScope + dispatchers.computing)
    }

    private fun loadItems() {
        launchJob {
            val items = repository.getCategoryItems(categoryName).map {
                PhrasebookCategoryItemUiModel(
                    text = UiText.DynamicString(it.word),
                    translation = UiText.DynamicString(it.translation)
                )
            }
            state.update {
                it.copy(
                    list = items,
                    uiState = UiState.Content
                )
            }
        }
    }

    data class PhrasebookCategoryState(
        override val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED,
        override val searchText: MutableStateFlow<String> = MutableStateFlow(""),
        val categoryName: UiText = UiText.DynamicString(""),
        val list: List<PhrasebookCategoryItemUiModel> = emptyList(),
        val uiState: UiState,
    ) : Searchable.SearchableState


    private companion object {
        const val SEARCH_DEBOUNCE = 300L
    }
}