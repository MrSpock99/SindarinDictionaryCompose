package apps.robot.sindarin_dictionary_en.base_ui.ad

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import apps.robot.sindarin_dictionary_en.base_ui.presentation.theme.CustomTheme
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun AdmobBanner(bannerId: String) {
    var isAdLoaded by remember {
        mutableStateOf(false)
    }
    if (!isAdLoaded) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(CustomTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = CustomTheme.colors.onBackground
            )
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context as Activity).apply {
                setAdSize(AdSize(-1, 60))
                adUnitId = bannerId
                loadAd(AdRequest.Builder().build())

                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        Log.d("Admob","banner load success")
                        isAdLoaded = true
                    }

                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        super.onAdFailedToLoad(p0)
                        Log.d("Admob","banner load error $p0")
                        isAdLoaded = true
                    }
                }
            }
        }
    )
}