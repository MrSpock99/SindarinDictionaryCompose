package apps.robot.sindarin_dictionary_en.main.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import apps.robot.sindarin_dictionary_en.bottomnav.navigation.SindarinBottomBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainFlow() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            bottomBar = {
                SindarinBottomBar(navController = navController)
            }
        ) { paddingValues ->
            Box(
                Modifier.padding(paddingValues)
            ) {
                AppNavGraph(navController = navController)
            }
        }
    }
}