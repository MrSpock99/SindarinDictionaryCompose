package apps.robot.favorites.impl.list.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import apps.robot.favorites.impl.list.presentation.model.FavoriteUiModel

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
            color = MaterialTheme.colors.onBackground,
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = item.translation.asString(),
            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}