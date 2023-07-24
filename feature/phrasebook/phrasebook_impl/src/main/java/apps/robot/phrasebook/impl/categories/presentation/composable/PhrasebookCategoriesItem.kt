package apps.robot.phrasebook.impl.categories.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import apps.robot.phrasebook.impl.categories.presentation.PhrasebookCategoryUiModel
import apps.robot.phrasebook.impl.category.presentation.PhrasebookCategoryItemUiModel

@Composable
fun PhrasebookCategoriesItem(item: PhrasebookCategoryUiModel, onClick: () -> Unit) {
    Text(
        text = item.text.asString(),
        color = MaterialTheme.colors.onBackground,
        fontSize = 32.sp,
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
            ),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}