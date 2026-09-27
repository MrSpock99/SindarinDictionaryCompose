package apps.robot.sindarin_dictionary_en.base_ui.ad

import android.content.Context
import android.util.Log
import apps.robot.sindarin_dictionary_en.base_ui.BuildConfig
import apps.robot.sindarin_dictionary_en.base_ui.presentation.ad.AdInitializer
import com.appodeal.ads.Appodeal
import com.appodeal.ads.initializing.ApdInitializationError

class AdInitializerImpl(private val context: Context) : AdInitializer {

    override fun onAppStartInit() {
        val appKey = BuildConfig.APPODEAL_APP_KEY
        if (appKey.isBlank()) {
            Log.e(TAG, "Appodeal App Key is missing. Pass -PappodealAppKey=<key> when building.")
            return
        }

        val bannerViewId = context.appodealBannerViewId()
        if (bannerViewId == 0) {
            Log.e(TAG, "Appodeal banner view resource was not found")
            return
        }
        Appodeal.setBannerViewId(bannerViewId)
        if (BuildConfig.DEBUG) Appodeal.setTesting(true)
        Appodeal.initialize(context, appKey, Appodeal.BANNER or Appodeal.INTERSTITIAL) { errors: List<ApdInitializationError>? ->
            if (errors.isNullOrEmpty()) {
                Log.d(TAG, "Appodeal initialization succeeded")
            } else {
                errors.forEach { Log.e(TAG, "Appodeal initialization failed", it) }
            }
        }
    }

    private companion object {
        const val TAG = "Appodeal"
    }
}
