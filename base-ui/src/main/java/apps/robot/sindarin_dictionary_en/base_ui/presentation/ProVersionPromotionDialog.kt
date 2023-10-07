package apps.robot.sindarin_dictionary_en.base_ui.presentation

import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import apps.robot.sindarin_dictionary_en.base_ui.R
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme

@Composable
fun ProVersionPromotionDialog(onConfirmClick: () -> Unit, onDismissClick: () -> Unit) {
    AlertDialog(
        backgroundColor = CustomTheme.colors.surface,
        onDismissRequest = { onDismissClick() },
        title = {
            Text(stringResource(id = R.string.pro_version_promo_dialog_title), color = CustomTheme.colors.onSurface)
        },
        text = {
            Text(
                stringResource(id = R.string.pro_version_promo_dialog_text),
                color = CustomTheme.colors.onSurface
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmClick() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = CustomTheme.colors.primary,
                    contentColor = CustomTheme.colors.onSurface,
                    disabledContentColor = CustomTheme.colors.onSurface
                        .copy(alpha = ContentAlpha.disabled)
                ),
            ) {
                Text(stringResource(id = R.string.pro_version_promo_dialog_button_confirm).uppercase())
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissClick() }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = CustomTheme.colors.surface,
                    contentColor = CustomTheme.colors.onSurface,
                    disabledContentColor = CustomTheme.colors.onSurface
                        .copy(alpha = ContentAlpha.disabled)
                )
            ) {
                Text(stringResource(id = R.string.pro_version_promo_dialog_button_dismiss).uppercase())
            }
        }
    )
}