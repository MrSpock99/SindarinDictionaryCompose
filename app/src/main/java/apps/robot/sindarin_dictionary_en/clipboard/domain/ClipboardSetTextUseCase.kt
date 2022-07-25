package apps.robot.sindarin_dictionary_en.clipboard.domain

class ClipboardSetTextUseCase(
    private val repository: ClipboardRepository
) {
    operator fun invoke(label: String, text: CharSequence) {
        repository.setText(label = label, text = text)
    }
}