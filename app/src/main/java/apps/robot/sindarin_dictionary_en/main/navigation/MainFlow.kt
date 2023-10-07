package apps.robot.sindarin_dictionary_en.main.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
            modifier = Modifier
                .padding(paddingValues)
        ) {
            AppNavGraph(navController = navController)
        }
    }
}