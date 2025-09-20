package di

import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext

@Composable
fun KoinApplication(content: @Composable () -> Unit) {
    KoinContext {
        content()
    }
}