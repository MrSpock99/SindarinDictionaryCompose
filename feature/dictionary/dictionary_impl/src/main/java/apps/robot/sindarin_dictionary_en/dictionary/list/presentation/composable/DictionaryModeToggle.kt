package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import apps.robot.dictionary.impl.R
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.UiState
import apps.robot.sindarin_dictionary_en.dictionary.api.domain.DictionaryMode
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.DictionaryListViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun DictionaryModeToggle(
    owner: ViewModelStoreOwner,
    viewModel: DictionaryListViewModel = getViewModel(owner = owner)
) {
    val state by viewModel.state.collectAsState()

    val firstText = stringResource(id = R.string.dictionary_mode_eng_to_elv)
    val secondText = stringResource(id = R.string.dictionary_mode_elv_to_eng)
    var toggleText by rememberSaveable {
        mutableStateOf(
            when (state.dictionaryMode) {
                DictionaryMode.ENGLISH_TO_ELVISH -> firstText
                DictionaryMode.ELVISH_TO_ENGLISH -> secondText
            }
        )
    }

    if (state.uiState == UiState.Content) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(60.dp)
                .clickable {
                    toggleText = if (toggleText == firstText) {
                        viewModel.onModeChange(DictionaryMode.ELVISH_TO_ENGLISH)
                        secondText
                    } else {
                        viewModel.onModeChange(DictionaryMode.ENGLISH_TO_ELVISH)
                        firstText
                    }
                }
        ) {
            Text(
                text = toggleText,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 16.sp,
                maxLines = 1
            )
        }
    } else {
        CircularProgressIndicator(
            color = MaterialTheme.colors.onPrimary
        )
    }
}
