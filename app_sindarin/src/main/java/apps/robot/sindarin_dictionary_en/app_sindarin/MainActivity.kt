package apps.robot.sindarin_dictionary_en.app_sindarin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme
import apps.robot.sindarin_dictionary_en.main.navigation.MainFlow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CustomTheme() {
                MainFlow()
            }
        }
    }
}