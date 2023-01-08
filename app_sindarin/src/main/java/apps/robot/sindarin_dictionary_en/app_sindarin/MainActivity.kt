package apps.robot.sindarin_dictionary_en.app_sindarin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import apps.robot.sindarin_dictionary_en.main.navigation.AppNavGraph
import apps.robot.sindarin_dictionary_en.main.navigation.MainFlow
import apps.robot.sindarin_dictionary_en.ui.theme.SindarinDictionaryComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SindarinDictionaryComposeTheme {
                MainFlow()
            }
        }
    }
}