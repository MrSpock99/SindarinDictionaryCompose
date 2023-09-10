package apps.robot.grammar.impl.grammar.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import apps.robot.grammar.impl.GrammarInternalFeature
import apps.robot.grammar.impl.R
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.DictionaryListTopAppBar
import org.koin.androidx.compose.get

@Composable
internal fun GrammarListScreen(grammarInternalFeature: GrammarInternalFeature = get(), navigator: NavHostController) {
    Scaffold(
        topBar = {
            DictionaryListTopAppBar(
                isTopBarVisible = true,
                searchWidgetState = SearchWidgetState.CLOSED,
                onSearchToggle = { },
                onTextChange = { },
                searchTextState = "",
                title = stringResource(R.string.topbar_header),
                hint = "",
                isSearchVisible = false,
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column {
                Text(
                    text = "Pronunciation",
                    Modifier.clickable {
                        navigator.navigate(grammarInternalFeature.pronounceScreen())
                    }
                )
                Text(text = "Stresses")
                Text(text = "Plural")
            }
        }
    }
}