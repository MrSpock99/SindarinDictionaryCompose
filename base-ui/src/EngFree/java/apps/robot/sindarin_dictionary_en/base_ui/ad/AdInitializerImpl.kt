package apps.robot.sindarin_dictionary_en.base_ui.ad

import android.content.Context
import android.util.Log
import apps.robot.sindarin_dictionary_en.base_ui.presentation.ad.AdInitializer
import com.google.android.gms.ads.MobileAds

class AdInitializerImpl(private val context: Context) : AdInitializer {

    override fun onAppStartInit() {
        MobileAds.initialize(context) {
            Log.d("Admob", "initialization status $it")
        }
    }
}