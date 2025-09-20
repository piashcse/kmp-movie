package piashcse.kmp.movie

import ui.App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import di.KoinApplication

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // Ensure the decor fits system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            KoinApplication {
                App()
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}