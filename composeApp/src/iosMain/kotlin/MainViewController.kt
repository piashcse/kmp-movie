import androidx.compose.ui.window.ComposeUIViewController
import di.initKoinPlatform
import kotlinx.coroutines.ExperimentalCoroutinesApi
import platform.UIKit.UIViewController
import ui.App

@OptIn(ExperimentalCoroutinesApi::class)
fun MainViewController(): UIViewController = ComposeUIViewController {
    App()
}.also {
    initKoinPlatform()
}