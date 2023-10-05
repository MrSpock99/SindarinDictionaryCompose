package apps.robot.phrasebook.impl.category.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import apps.robot.phrasebook.impl.R
import apps.robot.phrasebook.impl.category.presentation.PhrasebookCategoryViewModel
import apps.robot.sindarin_dictionary_en.base_ui.ad.AdmobBanner
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.dictionary.api.DictionaryFeatureApi
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DetailsMode
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.DictionaryListTopAppBar
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun PhrasebookCategory(
    categoryName: String,
    navigator: NavController,
    viewModel: PhrasebookCategoryViewModel = getViewModel(),
    dictionaryFeatureApi: DictionaryFeatureApi = get(),
) {
    val state by viewModel.state.collectAsState()

    viewModel.onReceiveArgs(categoryName)

    Scaffold(
        topBar = {
            DictionaryListTopAppBar(
                isTopBarVisible = true,
                searchWidgetState = state.searchWidgetState,
                onSearchToggle = viewModel::onSearchToggle,
                onTextChange = viewModel::onSearchTextChange,
                searchTextState = state.searchText.collectAsState().value,
                title = state.categoryName.asString(),
                hint = stringResource(R.string.phrasebook_appbar_hint),
                onBackClicked = navigator::navigateUp
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
        ) {
            val list = state.list

            if (list.isEmpty() && state.uiState == UiState.Content && state.searchWidgetState == SearchWidgetState.OPENED) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = stringResource(id = R.string.dictionary_list_nothing_found),
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                )
            } else if (list.isEmpty() && state.uiState == UiState.Content && state.searchWidgetState == SearchWidgetState.CLOSED) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = stringResource(id = R.string.phrasebook_empty_list),
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                )
            }

            if (state.uiState == UiState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }

            val context = LocalContext.current

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                list.forEachIndexed { index, item ->
                    if (index == 0) {
                        AdmobBanner()
                    }
                    PhrasebookCategoriesItem(item = item) {
                        navigator.navigate(
                            dictionaryFeatureApi.detailsRoute(
                                text = list[index].text.asString(context),
                                translation = list[index].translation.asString(context),
                                detailsMode = DetailsMode.PHRASEBOOK.name
                            )
                        )
                    }
                    if (index < list.lastIndex)
                        Divider(color = MaterialTheme.colors.onBackground, thickness = 1.dp)
                    else if (index == list.lastIndex)
                        AdmobBanner()
                }
            }
        }
    }
}