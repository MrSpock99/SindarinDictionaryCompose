package apps.robot.sindarin_dictionary_en.main.navigation

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import apps.robot.sindarin_dictionary_en.NavGraphs
import apps.robot.sindarin_dictionary_en.bottomnav.navigation.SindarinBottomBar
import com.ramcosta.composedestinations.DestinationsNavHost

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainFlow() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            bottomBar = {
                SindarinBottomBar(navController = navController)
            }
        ) {
            DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
        }
    }
}