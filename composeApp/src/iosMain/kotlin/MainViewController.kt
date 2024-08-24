import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun MainViewController() = ComposeUIViewController { App() }