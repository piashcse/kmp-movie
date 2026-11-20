import androidx.compose.ui.window.ComposeUIViewController
import di.initKoinPlatform
import platform.UIKit.UIViewController
import ui.App

fun MainViewController(): UIViewController {
    // Start Koin before the composition runs so ViewModels can be resolved on first frame.
    initKoinPlatform()
    return ComposeUIViewController {
        App()
    }
}
