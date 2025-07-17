import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.App

@OptIn(ExperimentalCoroutinesApi::class)
fun MainViewController() = ComposeUIViewController { App() }