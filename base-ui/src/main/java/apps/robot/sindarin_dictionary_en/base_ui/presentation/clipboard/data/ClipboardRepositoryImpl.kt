package apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard.data

import android.content.ClipData
import android.content.ClipboardManager
import apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard.domain.ClipboardRepository

class ClipboardRepositoryImpl(
    private val clipboardManager: ClipboardManager
) : ClipboardRepository {

    override fun setText(label: CharSequence, text: CharSequence) {
        val clipData = ClipData.newPlainText(label, text)

        clipboardManager.setPrimaryClip(clipData)
    }
}