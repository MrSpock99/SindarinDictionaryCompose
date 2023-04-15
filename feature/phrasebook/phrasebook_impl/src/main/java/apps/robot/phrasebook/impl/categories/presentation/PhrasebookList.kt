package apps.robot.phrasebook.impl.categories.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import apps.robot.phrasebook.impl.R
import apps.robot.phrasebook.impl.navigation.PhrasebookInternalFeature
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.DictionaryListTopAppBar
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
internal fun PhrasebookCategoriesList(
    viewModel: PhrasebookCategoriesViewModel = getViewModel(),
    phrasebookInternalFeature: PhrasebookInternalFeature = get(),
    navigator: NavHostController
) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isTopBarVisible = currentDestination?.route != DictionaryFeatureApi.DETAILS_ROUTE
    Scaffold(
        topBar = {
            DictionaryListTopAppBar(
                isTopBarVisible = isTopBarVisible,
                searchWidgetState = state.searchWidgetState,
                onSearchToggle = viewModel::onSearchToggle,
                onTextChange = viewModel::onSearchTextChange,
                searchTextState = state.searchText.collectAsState().value,
                title = stringResource(R.string.phrasebook_appbar_title),
                hint = stringResource(R.string.phrasebook_appbar_hint),
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
        ) {
            val list = state.categoriesList
            if (list.isEmpty() && state.searchWidgetState == SearchWidgetState.OPENED) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = stringResource(id = R.string.dictionary_list_nothing_found),
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                )
            } else if (list.isEmpty() && state.searchWidgetState == SearchWidgetState.CLOSED) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = stringResource(id = R.string.phrasebook_empty_list),
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                )
            }
            val listState = rememberLazyListState()
            val context = LocalContext.current
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(end = 16.dp),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    bottom = 16.dp
                )
            ) {
                items(
                    count = list.size,
                    key = { index ->
                        list[index].text.asString(context)
                    }) { index ->
                    Text(
                        text = list[index].text.asString(),
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(
                                    phrasebookInternalFeature.phrasebookCategoryScreen(
                                        categoryName = list[index].text.asString(context)
                                    )
                                )
                            },
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}