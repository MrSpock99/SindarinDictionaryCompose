package apps.robot.sindarin_dictionary_en.base_ui.ad

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import apps.robot.sindarin_dictionary_en.base_ui.R
import apps.robot.sindarin_dictionary_en.base_ui.presentation.findActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

var interstitialAd1: InterstitialAd? = null

fun loadInterstitial(context: Context) {
    if (canShowAd(context)) {
        InterstitialAd.load(
            context,
            context.resources.getString(R.string.admob_interstitial_id),
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd1 = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd1 = interstitialAd
                }
            }
        )
    }
}

fun showInterstitial(context: Context) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    if (canShowAd(context)) {

        val activity = context.findActivity()

        if (interstitialAd1 != null && activity != null) {
            interstitialAd1?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    removeInterstitial()
                }

                override fun onAdDismissedFullScreenContent() {
                    removeInterstitial()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    preferences.edit {
                        putLong(LATEST_SHOW_TIME, System.currentTimeMillis())
                    }
                }
            }
            interstitialAd1?.show(activity)
        }
    }
}

private fun removeInterstitial() {
    interstitialAd1?.fullScreenContentCallback = null
    interstitialAd1 = null
}

private fun canShowAd(context: Context): Boolean {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val latestShowTime = preferences.getLong(LATEST_SHOW_TIME, 0L)
    val currentTime = System.currentTimeMillis()

    return currentTime - latestShowTime >= FIVE_MINUTES_IN_MS
}

private const val FIVE_MINUTES_IN_MS = 300000
private const val LATEST_SHOW_TIME = "latest_ad_show_time"