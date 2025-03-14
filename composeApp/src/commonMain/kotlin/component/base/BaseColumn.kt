package component.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BaseColumn(
    loading: Boolean,
    errorMessage: String?,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
    if (!errorMessage.isNullOrEmpty()) {
        AlertDialog(
            onDismissRequest = { onDismissError() },
            confirmButton = {
                Button(onClick = { onDismissError() }) {
                    Text("OK")
                }
            },
            title = { Text("Error") },
            text = { Text(errorMessage) }
        )
    }
}