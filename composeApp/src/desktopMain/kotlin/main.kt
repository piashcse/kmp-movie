import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import moe.tlaster.precompose.ProvidePreComposeLocals

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kmp-movie",
    ) {
        ProvidePreComposeLocals {
            App()
        }
    }
}