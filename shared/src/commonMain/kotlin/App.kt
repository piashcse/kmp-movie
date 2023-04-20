import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import ui.component.AppBarWithArrow

@Composable
internal fun App() {
    val navigator = rememberNavigator()
    val canGoBack = navigator.canGoBack.collectAsState(false)
    MaterialTheme {
        Scaffold(topBar = {
            AppBarWithArrow("Movie World", isBackEnable = canGoBack.value) {
                navigator.goBack()
            }
        }) {
            Navigation(navigator)
        }
    }
}