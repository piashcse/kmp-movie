import androidx.compose.ui.window.ComposeUIViewController
import di.initKoinPlatform
import org.koin.mp.KoinPlatform
import platform.UIKit.UIViewController
import ui.App

fun MainViewController(): UIViewController {
    // Don't start Koin twice when the controller is recreated (e.g. multiple scenes).
    if (KoinPlatform.getKoinOrNull() == null) {
        initKoinPlatform()
    }
    return ComposeUIViewController {
        App()
    }
}
