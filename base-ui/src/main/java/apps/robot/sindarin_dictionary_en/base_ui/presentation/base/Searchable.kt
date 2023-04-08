package apps.robot.sindarin_dictionary_en.base_ui.presentation.base

import kotlinx.coroutines.flow.MutableStateFlow

interface Searchable {
    fun onSearchToggle()
    fun onSearchTextChange(searchText: String)

    interface SearchableState {
        val searchWidgetState: SearchWidgetState
        val searchText: MutableStateFlow<String>
    }
}