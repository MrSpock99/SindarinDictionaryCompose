package apps.robot.favorites.impl.list.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import apps.robot.favorites.impl.list.presentation.model.FavoriteUiModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme

@Composable
fun FavoriteItem(item: FavoriteUiModel, onClick: () -> Unit) {
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
            text = item.text.asString(),
            color = CustomTheme.colors.onBackground,
            fontSize = CustomTheme.typography.listItemTitle.fontSize,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = item.translation.asString(),
            color = CustomTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
            fontSize = CustomTheme.typography.listItemSubtitle.fontSize,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}