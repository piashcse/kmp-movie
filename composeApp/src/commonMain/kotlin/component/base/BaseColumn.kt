package component.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BaseColumn(
    loading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var errorState by remember { mutableStateOf<String?>(null) }

    // Update errorState whenever errorMessage changes
    LaunchedEffect(errorMessage) {
        errorState = errorMessage
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (loading) Arrangement.Center else Arrangement.Top
    ) {
        when {
            loading -> {
                CircularProgressIndicator()
            }

            else -> {
                content()
            }
        }
    }

    // Show Error Alert Dialog
    errorState?.let {
        ErrorAlert(errorMessage = it) {
            // Clear the error state when the "OK" button is clicked
            errorState = null
        }
    }
}

@Composable
fun ErrorAlert(errorMessage: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Error") },
        text = { Text(text = errorMessage) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        })
}
