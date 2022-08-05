package apps.robot.sindarin_dictionary_en.dictionary.list.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import apps.robot.sindarin_dictionary_en.R
import apps.robot.sindarin_dictionary_en.dictionary.list.presentation.model.SearchWidgetState

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun DictionaryListTopAppBar(
    isTopBarVisible: Boolean,
    viewModelStoreOwner: ViewModelStoreOwner,
    searchWidgetState: SearchWidgetState,
    onSearchToggle: () -> Unit,
    onTextChange: (String) -> Unit,
    searchTextState: String
) {
    AnimatedVisibility(visible = isTopBarVisible) {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {
                DictionaryModeAppBar(
                    viewModelStoreOwner = viewModelStoreOwner,
                    onSearchToggle = onSearchToggle
                )
            }
            SearchWidgetState.OPENED -> {
                SearchAppBar(
                    text = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = { onSearchToggle() }
                )
            }
        }
    }
}

@Composable
fun DictionaryModeAppBar(
    viewModelStoreOwner: ViewModelStoreOwner,
    onSearchToggle: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.top_bar_dictionary_title),
            fontSize = 22.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(
            modifier = Modifier.weight(
                weight = 1f,
                fill = true
            )
        )
        DictionaryModeToggle(owner = viewModelStoreOwner)
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium),
            onClick = {
                onSearchToggle()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colors.onSurface
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.surface
    ) {
        val focusRequester = remember { FocusRequester() }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.dictionary_searchbar_hint),
                    color = MaterialTheme.colors.onSurface
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {
                        onCloseClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                focusedLabelColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

}