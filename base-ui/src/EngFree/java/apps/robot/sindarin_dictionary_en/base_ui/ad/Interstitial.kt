package apps.robot.sindarin_dictionary_en.base_ui.ad

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import apps.robot.sindarin_dictionary_en.base_ui.presentation.findActivity
import com.appodeal.ads.Appodeal

fun loadInterstitial(context: Context) {
    val activity = context.findActivity() ?: return
    if (canShowAd(context) && Appodeal.isInitialized(Appodeal.INTERSTITIAL)) {
        Appodeal.cache(activity, Appodeal.INTERSTITIAL)
    }
}

fun showInterstitial(context: Context) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    if (canShowAd(context)) {

        val activity = context.findActivity()

        if (activity != null &&
            Appodeal.isInitialized(Appodeal.INTERSTITIAL) &&
            Appodeal.isLoaded(Appodeal.INTERSTITIAL) &&
            Appodeal.show(activity, Appodeal.INTERSTITIAL)
        ) {
            preferences.edit {
                putLong(LATEST_SHOW_TIME, System.currentTimeMillis())
            }
        }
    }
}

private fun canShowAd(context: Context): Boolean {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val latestShowTime = preferences.getLong(LATEST_SHOW_TIME, 0L)
    val currentTime = System.currentTimeMillis()

    return currentTime - latestShowTime >= FIVE_MINUTES_IN_MS
}

private const val FIVE_MINUTES_IN_MS = 300000
private const val LATEST_SHOW_TIME = "latest_ad_show_time"
