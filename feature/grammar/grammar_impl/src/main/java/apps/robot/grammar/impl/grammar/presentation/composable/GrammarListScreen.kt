package apps.robot.grammar.impl.grammar.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                title = stringResource(R.string.topbar_grammar_header),
                hint = "",
                isSearchVisible = false,
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column {
                GrammarCategoryItem(name = stringResource(R.string.grammar_item_pronounce)) {
                    navigator.navigate(grammarInternalFeature.pronounceScreen())
                }
                GrammarCategoryItem(name = stringResource(R.string.grammar_item_stresses)) {

                }
                GrammarCategoryItem(name = stringResource(R.string.grammar_item_plural)) {

                }
            }
        }
    }
}

@Composable
fun GrammarCategoryItem(name: String, onClick: () -> (Unit)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    bottom = 16.dp
                )
            )
    ) {
        Text(
            text = name,
            color = MaterialTheme.colors.onBackground,
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}