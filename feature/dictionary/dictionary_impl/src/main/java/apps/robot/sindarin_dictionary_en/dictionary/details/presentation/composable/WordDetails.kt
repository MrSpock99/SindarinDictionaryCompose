package apps.robot.sindarin_dictionary_en.dictionary.details.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import apps.robot.dictionary.impl.R
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.DetailsAction
import apps.robot.sindarin_dictionary_en.dictionary.details.presentation.DetailsViewModel
import apps.robot.sindarin_dictionary_en.dictionary.list.domain.DictionaryMode
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WordDetails(
    viewModel: DetailsViewModel = getViewModel(),
    wordId: String,
    dictionaryMode: DictionaryMode,
    navigator: NavHostController
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(key1 = Unit) {
        viewModel.onReceiveArgs(wordId, dictionaryMode)
    }
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navigator.navigateUp()
                        },
                    painter = painterResource(id = R.drawable.top_bar_arrow_ic),
                    contentDescription = null
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onFavoriteBtnClick()
                }
            ) {
                Icon(
                    painter = if (state.isFavorite) {
                        painterResource(id = R.drawable.details_favorite_on_ic)
                    } else {
                        painterResource(id = R.drawable.details_favorite_off_ic)
                    },
                    contentDescription = null
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = state.word,
                fontSize = 40.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable {
                        viewModel.onTextClick(state.word)
                    }
            )
            Text(
                text = state.translation,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable {
                        viewModel.onTextClick(state.translation)
                    }
            )
        }
    }
    val context = LocalContext.current
    state.actions.firstOrNull()?.let {
        when (it) {
            is DetailsAction.ShowSnackbar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(it.text.asString(context))
                }
            }
        }
        viewModel.clearAction(it.id)
    }
}

@Composable
@Preview
fun WordDetailsPreview() {
    //WordDetails(wordId = "", dictionaryMode = DictionaryMode.ENGLISH_TO_ELVISH)
}