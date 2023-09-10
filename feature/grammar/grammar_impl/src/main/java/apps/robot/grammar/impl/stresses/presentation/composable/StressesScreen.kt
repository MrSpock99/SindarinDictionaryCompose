package apps.robot.grammar.impl.stresses.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import apps.robot.grammar.impl.R
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.DictionaryListTopAppBar

@Composable
fun StressesScreen(navigator: NavController) {
    Scaffold(
        topBar = {
            DictionaryListTopAppBar(
                isTopBarVisible = true,
                searchWidgetState = SearchWidgetState.CLOSED,
                onSearchToggle = { },
                onTextChange = { },
                searchTextState = "",
                title = stringResource(R.string.topbar_stresses_header),
                hint = "",
                isSearchVisible = false,
                onBackClicked = navigator::navigateUp
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.stresses_text)
            );
        }
    }
}