package piashcse.kmp.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.App

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // Ensure the decor fits system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            App()
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}