package apps.robot.sindarin_dictionary_en.base_ui.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@Immutable
data class CustomColors(
    val primary: Color,
    val onPrimary: Color,
    val background: Color,
    val onBackground: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val surface: Color,
    val onSurface: Color
)

@Immutable
data class CustomTypography(
    val listItemTitle: TextStyle,
    val listItemSubtitle: TextStyle,
    val singleListItemTitle: TextStyle,
    val subtitle: TextStyle
)

@Immutable
data class CustomElevation(
    val default: Dp,
    val pressed: Dp
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        onBackground = Color.Unspecified,
        background = Color.Unspecified,
        secondary = Color.Unspecified,
        secondaryVariant = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
    )
}
val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        listItemTitle = TextStyle.Default,
        listItemSubtitle = TextStyle.Default,
        singleListItemTitle = TextStyle.Default,
        subtitle = TextStyle.Default,
    )
}
val LocalCustomElevation = staticCompositionLocalOf {
    CustomElevation(
        default = Dp.Unspecified,
        pressed = Dp.Unspecified
    )
}

@Composable
fun CustomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val customColors = if (darkTheme) {
        CustomColors(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            onBackground = md_theme_dark_onBackground,
            background = md_theme_dark_background,
            secondary = md_theme_dark_secondary,
            secondaryVariant = md_theme_dark_secondary,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
        )
    } else {
        CustomColors(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            onBackground = md_theme_light_onBackground,
            background = md_theme_light_background,
            secondary = md_theme_light_secondary,
            secondaryVariant = md_theme_light_secondary,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
        )
    }

    val customTypography = CustomTypography(
        listItemTitle = TextStyle(fontSize = 24.sp),
        listItemSubtitle = TextStyle(fontSize = 18.sp),
        singleListItemTitle = TextStyle(fontSize = 24.sp),
        subtitle = TextStyle(fontSize = 12.sp),
    )
    val customElevation = CustomElevation(
        default = 4.dp,
        pressed = 8.dp
    )
    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalCustomTypography provides customTypography,
        LocalCustomElevation provides customElevation,
        content = content
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insets = WindowCompat.getInsetsController(window, view)
            window.statusBarColor = customColors.primary.toArgb() // choose a status bar color
            window.navigationBarColor = customColors.primary.toArgb() // choose a navigation bar color
            insets.isAppearanceLightStatusBars = darkTheme
            insets.isAppearanceLightNavigationBars = darkTheme
        }
    }
}

// Use with eg. CustomTheme.elevation.small
object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current
    val elevation: CustomElevation
        @Composable
        get() = LocalCustomElevation.current
}