import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.initKoinPlatform
import java.util.prefs.Preferences
import ui.App

private const val PREFS_NODE = "piashcse/kmp-movie/desktop"

fun main() = application {
    initKoinPlatform()

    val prefs = Preferences.userRoot().node(PREFS_NODE)
    val savedWidth = prefs.getInt("width", 1100)
    val savedHeight = prefs.getInt("height", 720)
    val savedX = prefs.getInt("x", Int.MIN_VALUE)
    val savedY = prefs.getInt("y", Int.MIN_VALUE)
    val windowState = rememberWindowState(
        width = savedWidth.dp,
        height = savedHeight.dp,
        position = if (savedX != Int.MIN_VALUE && savedY != Int.MIN_VALUE) {
            WindowPosition(savedX.dp, savedY.dp)
        } else {
            WindowPosition(Alignment.Center)
        },
        placement = if (prefs.getBoolean("maximized", false)) {
            WindowPlacement.Maximized
        } else {
            WindowPlacement.Floating
        },
    )

    Window(
        onCloseRequest = {
            prefs.putInt("width", windowState.size.width.value.toInt())
            prefs.putInt("height", windowState.size.height.value.toInt())
            if (windowState.position.isSpecified) {
                prefs.putInt("x", windowState.position.x.value.toInt())
                prefs.putInt("y", windowState.position.y.value.toInt())
            }
            prefs.putBoolean("maximized", windowState.placement == WindowPlacement.Maximized)
            prefs.flush()
            exitApplication()
        },
        state = windowState,
        title = "kmp-movie",
    ) {
        App()
    }
}
