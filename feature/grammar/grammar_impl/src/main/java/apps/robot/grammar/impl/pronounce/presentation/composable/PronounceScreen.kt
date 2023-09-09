package apps.robot.grammar.impl.pronounce.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import apps.robot.grammar.impl.R
import apps.robot.grammar.impl.pronounce.presentation.PronounceViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.BorderOrder
import apps.robot.sindarin_dictionary_en.base_ui.presentation.drawSegmentedBorder
import org.koin.androidx.compose.getViewModel

@Composable
fun PronounceScreen() {
    TableScreen()
}

@Composable
fun TableScreen(viewModel: PronounceViewModel = getViewModel()) {
    val state by viewModel.uiState.collectAsState()

    val column1Weight = .2f
    val column2Weight = .4f
    val column3Weight = .4f

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(
                Modifier
                    .height(IntrinsicSize.Min)
                    .background(MaterialTheme.colors.secondaryVariant)
                    .drawSegmentedBorder(
                        strokeWidth = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        borderOrder = BorderOrder.Start,
                        drawDivider = true,
                        cornerPercent = 0
                    )
                    .drawSegmentedBorder(
                        strokeWidth = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        borderOrder = BorderOrder.End,
                        drawDivider = true,
                        cornerPercent = 0
                    )
            ) {
                TableCell(text = stringResource(id = R.string.pronounce_header_1), weight = column1Weight)
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(text = stringResource(id = R.string.pronounce_header_2), weight = column2Weight)
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(text = stringResource(id = R.string.pronounce_header_3), weight = column3Weight)
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
                        color = MaterialTheme.colors.onBackground,
                        borderOrder = BorderOrder.Start,
                        drawDivider = true,
                        cornerPercent = 0
                    )
                    .drawSegmentedBorder(
                        strokeWidth = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        borderOrder = BorderOrder.End,
                        drawDivider = true,
                        cornerPercent = 0
                    )
            ) {
                TableCell(text = item.sound, weight = column1Weight)
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(text = item.example, weight = column2Weight)
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TableCell(text = item.sindarinExample, weight = column3Weight)
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    textColor: Color = MaterialTheme.colors.onBackground
) {
    val modifier = Modifier
        .weight(weight)
        .padding(8.dp)

    Text(
        text = text,
        modifier = modifier,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        color = textColor
    )
}