package apps.robot.sindarin_dictionary_en.dictionary.details.presentation

import apps.robot.sindarin_dictionary_en.R
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.clipboard.domain.ClipboardSetTextUseCase
import apps.robot.sindarin_dictionary_en.dictionary.base.domain.Word
import apps.robot.sindarin_dictionary_en.dictionary.details.domain.DictionaryGetWordByIdUseCase
import apps.robot.sindarin_dictionary_en.dictionary.details.domain.DictionaryUpdateWordUseCase
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.model.WordDetailsState
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class DetailsViewModel(
    private val getWordById: DictionaryGetWordByIdUseCase,
    private val setTextToClipboard: ClipboardSetTextUseCase,
    private val updateWord: DictionaryUpdateWordUseCase
) : BaseViewModel() {
    val state = MutableStateFlow(WordDetailsState())
    private var word: Word? = null
    private var dictionaryMode: DictionaryMode? = null

    fun onReceiveArgs(id: String, dictionaryMode: DictionaryMode) {
        launchJob {
            this.dictionaryMode = dictionaryMode
            word = getWordById(wordId = id, mode = dictionaryMode)
            state.value = WordDetailsState(
                word = word?.word ?: "",
                translation = word?.translation ?: "",
                isFavorite = word?.isFavorite ?: false
            )
        }
    }

    fun onFavoriteBtnClick() {
        state.value = state.value.copy(isFavorite = state.value.isFavorite.not())
        launchJob {
            updateWord(
                dictionaryMode = dictionaryMode, word = word?.copy(isFavorite = state.value.isFavorite)
            )
        }
        val actions = state.value.actions.toMutableList().apply {
            add(
                DetailsAction.ShowSnackbar(
                    UiText.StringResource(
                        if (state.value.isFavorite) {
                            R.string.details_add_favorites
                        } else {
                            R.string.details_remove_favorites
                        }
                    )
                )
            )
        }
        state.update {
            it.copy(actions = actions)
        }
    }

    fun onTextClick(text: String) {
        setTextToClipboard(text, text)
        val actions = state.value.actions.toMutableList().apply {
            add(DetailsAction.ShowSnackbar(UiText.StringResource(R.string.details_copy_clipboard)))
        }
        state.update {
            it.copy(actions = actions)
        }
    }

    fun clearAction(id: String) {
        state.update { it.copy(actions = it.actions.filter { it.id != id }) }
    }
}

sealed class DetailsAction(val id: String = UUID.randomUUID().toString()) {
    class ShowSnackbar(val text: UiText) : DetailsAction()
}