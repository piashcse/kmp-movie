import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.App
import di.initKoinPlatform
import di.KoinApplication
import platform.UIKit.UIViewController

@OptIn(ExperimentalCoroutinesApi::class)
fun MainViewController(): UIViewController = ComposeUIViewController {
    KoinApplication {
        App()
    }
}.also {
    initKoinPlatform()
}