package apps.robot.grammar.impl.plural.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import apps.robot.grammar.impl.R
import apps.robot.grammar.impl.plural.presentation.PluralViewModel
import apps.robot.grammar.impl.pronounce.presentation.composable.TableCell
import apps.robot.sindarin_dictionary_en.base_ui.presentation.BorderOrder
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState
import apps.robot.sindarin_dictionary_en.base_ui.presentation.drawSegmentedBorder
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme
import apps.robot.sindarin_dictionary_en.dictionary.api.presentation.DictionaryListTopAppBar
import org.koin.androidx.compose.getViewModel

@Composable
fun PluralScreen(navigator: NavController) {
    Scaffold(
        topBar = {
            DictionaryListTopAppBar(
                isTopBarVisible = true,
                searchWidgetState = SearchWidgetState.CLOSED,
                onSearchToggle = { },
                onTextChange = { },
                searchTextState = "",
                title = stringResource(R.string.topbar_plural_header),
                hint = "",
                isSearchVisible = false,
                onBackClicked = navigator::navigateUp
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
            color = CustomTheme.colors.background
        ) {
            TableScreen()
        }
    }
}

@Composable
fun TableScreen(viewModel: PluralViewModel = getViewModel()) {
    val state by viewModel.uiState.collectAsState()

    val column1Weight = .2f
    val column2Weight = .2f
    val column3Weight = .30f
    val column4Weight = .30f

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(
                Modifier
                    .height(IntrinsicSize.Min)
                    .background(CustomTheme.colors.primary)
                    .drawSegmentedBorder(
                        strokeWidth = 1.dp,
                        color = CustomTheme.colors.onBackground,
                        borderOrder = BorderOrder.Start,
                        drawDivider = true,
                        cornerPercent = 0
                    )
                    .drawSegmentedBorder(
                        strokeWidth = 1.dp,
                        color = CustomTheme.colors.onBackground,
                        borderOrder = BorderOrder.End,
                        drawDivider = true,
                        cornerPercent = 0
                    )
            ) {
                TableCell(
                    text = stringResource(id = R.string.plural_header_1),
                    weight = column1Weight,
                    isBold = true,
                    textColor = CustomTheme.colors.onPrimary
                )
                Divider(
                    color = CustomTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(
                    text = stringResource(id = R.string.plural_header_2),
                    weight = column2Weight,
                    isBold = true,
                    textColor = CustomTheme.colors.onPrimary
                )
                Divider(
                    color = CustomTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(
                    text = stringResource(id = R.string.plural_header_3),
                    weight = column3Weight,
                    isBold = true,
                    textColor = CustomTheme.colors.onPrimary
                )
                Divider(
                    color = CustomTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(
                    text = stringResource(id = R.string.plural_header_4),
                    weight = column4Weight,
                    isBold = true,
                    textColor = CustomTheme.colors.onPrimary
                )
            }
        }

        val items = state.items
        items(
            count = items.size,
            key = { index ->
                items[index].id
            }) { index ->
            val item = items[index]
            Row(
                Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .drawSegmentedBorder(
                        strokeWidth = 1.dp,
                        color = CustomTheme.colors.onBackground,
                        borderOrder = BorderOrder.Start,
                        drawDivider = true,
                        cornerPercent = 0
                    )
                    .drawSegmentedBorder(
                        strokeWidth = 1.dp,
                        color = CustomTheme.colors.onBackground,
                        borderOrder = BorderOrder.End,
                        drawDivider = true,
                        cornerPercent = 0
                    )
            ) {
                TableCell(text = item.vowel, weight = column1Weight)
                Divider(
                    color = CustomTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(text = item.beginning, weight = column2Weight)
                Divider(
                    color = CustomTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(text = item.end, weight = column3Weight)
                Divider(
                    color = CustomTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(text = item.examples, weight = column4Weight)
            }
        }
    }
}