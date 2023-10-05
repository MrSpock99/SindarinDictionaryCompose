package apps.robot.sindarin_dictionary_en.base_ui.ad

import android.content.Context
import apps.robot.sindarin_dictionary_en.base_ui.presentation.ad.AdInitializer
import com.google.android.gms.ads.MobileAds

class AdInitializerImpl(private val context: Context) : AdInitializer {

    override fun onAppStartInit() {
        MobileAds.initialize(context) {
            // noting to do
        }
    }
}