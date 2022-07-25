package apps.robot.sindarin_dictionary_en.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SindarinDictionaryComposeTheme {
    }
}