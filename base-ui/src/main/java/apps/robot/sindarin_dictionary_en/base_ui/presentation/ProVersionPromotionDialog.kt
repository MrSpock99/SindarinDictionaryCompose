package apps.robot.sindarin_dictionary_en.base_ui.presentation

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import apps.robot.sindarin_dictionary_en.base_ui.R

@Composable
fun ProVersionPromotionDialog(onConfirmClick: () -> Unit, onDismissClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismissClick() },
        title = { Text(stringResource(id = R.string.pro_version_promo_dialog_title)) },
        text = { Text(stringResource(id = R.string.pro_version_promo_dialog_text)) },
        confirmButton = {
            TextButton(onClick = { onConfirmClick() }) {
                Text(stringResource(id = R.string.pro_version_promo_dialog_button_confirm).uppercase())
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClick() }) {
                Text(stringResource(id = R.string.pro_version_promo_dialog_button_dismiss).uppercase())
            }
        }
    )
}