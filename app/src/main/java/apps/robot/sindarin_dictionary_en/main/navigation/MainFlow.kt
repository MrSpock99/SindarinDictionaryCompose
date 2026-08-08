package apps.robot.sindarin_dictionary_en.main.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme

@Composable
fun MainFlow() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            SindarinBottomBar(navController = navController)
        },
        backgroundColor = CustomTheme.colors.background
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .safeDrawingPadding()
                    .background(CustomTheme.colors.background)
            ) {
                AppNavGraph(navController = navController)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .background(CustomTheme.colors.primary)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsBottomHeight(WindowInsets.navigationBars)
                    .background(CustomTheme.colors.primary)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
