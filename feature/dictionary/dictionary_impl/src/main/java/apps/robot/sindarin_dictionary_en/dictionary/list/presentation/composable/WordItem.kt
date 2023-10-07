package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.WordUiModel

@Composable
internal fun WordItem(wordUiModel: WordUiModel, onClick: (WordUiModel) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(wordUiModel)
            }
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    ) {
        Text(
            text = wordUiModel.word.asString(),
            color = CustomTheme.colors.onBackground,
            fontSize = CustomTheme.typography.listItemTitle.fontSize,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = wordUiModel.translation.asString(),
            color = CustomTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
            fontSize = CustomTheme.typography.listItemSubtitle.fontSize,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview(
    name = "My Preview",
    showBackground = true,
    backgroundColor = 0x989a82
)
@Composable
fun WordItemPreview() {
    Text(text = "fuck")
    WordItem(
        wordUiModel = WordUiModel(
            "",
            UiText.DynamicString("aasdasd"),
            UiText.DynamicString("bbbbb"),
            false
        ),
        onClick = {}
    )
}