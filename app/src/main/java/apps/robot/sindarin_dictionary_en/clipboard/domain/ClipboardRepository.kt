package apps.robot.sindarin_dictionary_en.clipboard.domain

interface ClipboardRepository {
    fun setText(label: CharSequence, text: CharSequence)
}