package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.WordUiModel

@Composable
fun WordItem(wordUiModel: WordUiModel, onClick: (WordUiModel) -> Unit) {
    Text(
        text = wordUiModel.word.asString(),
        color = MaterialTheme.colors.onBackground,
        fontSize = 32.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(wordUiModel)
            },
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}