package apps.robot.sindarin_dictionary_en.base_ui.presentation.base

import android.content.ClipboardManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appModule() = module {
    single { androidContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
}