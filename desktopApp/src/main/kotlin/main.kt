import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoinPlatform
import ui.App

fun main() = application {
    initKoinPlatform()
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-movie",
    ) {
        App()
    }
}
