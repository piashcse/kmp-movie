package utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.component.base.ErrorAlert

/**
 * Generic loading and error handling composable that can be reused across different screens
 */
@Composable
fun <T> GenericLoadingErrorState(
    data: T?,
    loading: Boolean,
    errorMessage: String?,
    onRetry: (() -> Unit)? = null,
    content: @Composable (T) -> Unit
) {
    when {
        loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            ErrorAlert(errorMessage = errorMessage) {
                onRetry?.invoke()
            }
        }
        data != null -> {
            content(data)
        }
        else -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No data available")
            }
        }
    }
}