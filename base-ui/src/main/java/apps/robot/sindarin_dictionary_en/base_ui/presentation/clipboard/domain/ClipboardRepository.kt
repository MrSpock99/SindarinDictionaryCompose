package apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard.domain

interface ClipboardRepository {
    fun setText(label: CharSequence, text: CharSequence)
}