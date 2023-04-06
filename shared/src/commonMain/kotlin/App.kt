import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import ui.home.NowPlayingViewModel
import ui.home.HomeScreen

@Composable
internal fun App(viewModel: NowPlayingViewModel = NowPlayingViewModel()) {
    MaterialTheme {
        HomeScreen(viewModel)
    }
}