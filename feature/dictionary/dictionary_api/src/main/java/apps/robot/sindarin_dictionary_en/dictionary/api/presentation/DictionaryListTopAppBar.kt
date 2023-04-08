package apps.robot.sindarin_dictionary_en.dictionary.api.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelStoreOwner
import apps.robot.sindarin_dictionary_en.base_ui.presentation.base.SearchWidgetState

@Composable
fun DictionaryListTopAppBar(
    isTopBarVisible: Boolean,
    viewModelStoreOwner: ViewModelStoreOwner,
    searchWidgetState: SearchWidgetState,
    onSearchToggle: () -> Unit,
    onTextChange: (String) -> Unit,
    searchTextState: String,
    title: String,
    hint: String,
    optionalContent: (@Composable () -> Unit)? = null
) {
    AnimatedVisibility(visible = isTopBarVisible) {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {
                DictionaryModeAppBar(
                    viewModelStoreOwner = viewModelStoreOwner,
                    onSearchToggle = onSearchToggle,
                    title = title,
                    optionalContent = optionalContent
                )
            }
            SearchWidgetState.OPENED -> {
                SearchAppBar(
                    text = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = { onSearchToggle() },
                    hint = hint
                )
            }
        }
    }
}

@Composable
fun DictionaryModeAppBar(
    viewModelStoreOwner: ViewModelStoreOwner,
    onSearchToggle: () -> Unit,
    title: String,
    optionalContent: (@Composable () -> Unit)? = null
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = title,
            fontSize = 22.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(
            modifier = Modifier.weight(
                weight = 1f,
                fill = true
            )
        )
        if (optionalContent != null) {
            optionalContent()
        }
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

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    hint: String
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
                    text = hint,
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
                        onTextChange("")
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