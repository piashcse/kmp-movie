import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import moe.tlaster.precompose.ProvidePreComposeLocals
import ui.App
import di.initKoinPlatform
import di.KoinApplication

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = application {
    initKoinPlatform()
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-movie",
    ) {
        KoinApplication {
            ProvidePreComposeLocals {
                App()
            }
        }
    }
}