package apps.robot.sindarin_dictionary_en.dictionary.details.presentation

import android.content.Context
import apps.robot.dictionary.impl.R
import apps.robot.favorites.api.domain.FavoriteModel
import apps.robot.favorites.api.domain.FavoritesGetFavoriteByIdUseCase
import apps.robot.favorites.api.domain.FavoritesUpdateFavoriteStatusUseCase
import apps.robot.sindarin_dictionary_en.base_ui.presentation.UiText
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.BaseViewModel
import apps.robot.sindarin_dictionary_en.base_ui.presentation.clipboard.domain.ClipboardSetTextUseCase
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DetailsMode
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.model.WordDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

internal class DetailsViewModel(
    private val setTextToClipboard: ClipboardSetTextUseCase,
    private val updateFavoriteStatus: FavoritesUpdateFavoriteStatusUseCase,
    private val getFavoriteById: FavoritesGetFavoriteByIdUseCase
) : BaseViewModel() {

    val state = MutableStateFlow(WordDetailsState())

    fun onReceiveArgs(
        id: String,
        text: String?,
        translation: String?,
        detailsMode: DetailsMode?,
    ) {
        launchJob {
            val favoriteModel = getFavoriteById(id)

            if (detailsMode == DetailsMode.FAVORITES) {
                if (favoriteModel != null) {
                    state.value = WordDetailsState(
                        id = id,
                        word = UiText.DynamicString(favoriteModel.text),
                        translation = UiText.DynamicString(favoriteModel.translation),
                        isFavorite = true
                    )
                }
            } else if (detailsMode == DetailsMode.DICTIONARY) {
                state.value = WordDetailsState(
                    id = id,
                    word = UiText.DynamicString(text.orEmpty()),
                    translation = UiText.DynamicString(translation.orEmpty()),
                    isFavorite = favoriteModel != null
                )
            }
        }
    }

    fun onFavoriteBtnClick(context: Context) {
        val currentFavorite = state.value
        launchJob {
            updateFavoriteStatus(
                favoriteModel = FavoriteModel(
                    id = currentFavorite.id,
                    text = currentFavorite.word.asString(context),
                    translation = currentFavorite.translation.asString(context)
                ),
                isFavorite = state.value.isFavorite.not()
            )
        }
        val actions = state.value.actions.toMutableList().apply {
            add(
                DetailsAction.ShowSnackbar(
                    UiText.StringResource(
                        if (state.value.isFavorite.not()) {
                            R.string.details_add_favorites
                        } else {
                            R.string.details_remove_favorites
                        }
                    )
                )
            )
        }
        state.update {
            it.copy(
                actions = actions,
                isFavorite = state.value.isFavorite.not()
            )
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