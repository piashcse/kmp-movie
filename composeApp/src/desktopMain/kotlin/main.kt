import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoinPlatform
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.App

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = application {
    initKoinPlatform()
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-movie",
    ) {
        App()
    }
}