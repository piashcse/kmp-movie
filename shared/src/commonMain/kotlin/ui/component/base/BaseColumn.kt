package ui.component.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.action_ok
import kmp_movie.composeapp.generated.resources.error_title
import org.jetbrains.compose.resources.stringResource

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
        title = { Text(text = stringResource(Res.string.error_title)) },
        text = { Text(text = errorMessage) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.action_ok))
            }
        })
}
