package apps.robot.sindarin_dictionary_en.base_ui.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun openProVersionInMarket(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("market://details?id=apps.robot.sindarin_dictionary_en_noad")
    if (!isActivityStarted(intent, context)) {
        intent.data = Uri
            .parse("https://play.google.com/store/apps/details?id=apps.robot.sindarin_dictionary_en_noad")
        if (isActivityStarted(intent, context)) {
            Toast.makeText(
                context,
                "Could not open Android market, please check if the market app installed or not. Try again later",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

private fun isActivityStarted(aIntent: Intent, context: Context): Boolean {
    return try {
        context.startActivity(aIntent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}