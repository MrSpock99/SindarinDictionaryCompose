package apps.robot.sindarin_dictionary_en.base_ui.ad

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import apps.robot.sindarin_dictionary_en.base_ui.presentation.findActivity
import com.appodeal.ads.Appodeal

@Composable
fun AppodealBanner() {
    val context = LocalContext.current
    val activity = context.findActivity() ?: return
    val bannerViewId = context.appodealBannerViewId()
    if (bannerViewId == 0) return
    if (!Appodeal.isInitialized(Appodeal.BANNER)) return

    AndroidView(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        factory = {
            Appodeal.getBannerView(activity).apply {
                id = bannerViewId
            }
        }
    )

    DisposableEffect(activity) {
        activity.window.decorView.post { Appodeal.show(activity, Appodeal.BANNER_VIEW) }
        onDispose { Appodeal.hide(activity, Appodeal.BANNER_VIEW) }
    }
}
