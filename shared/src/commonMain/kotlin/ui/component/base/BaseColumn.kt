package ui.component.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun BaseColumn(
    loading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (loading) Arrangement.Center else Arrangement.Top,
    ) {
        when {
            loading -> CircularProgressIndicator()
            errorMessage != null -> ErrorState(
                message = errorMessage,
                onRetry = onRetry,
            )

            else -> content()
        }
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: (() -> Unit)?,
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (onRetry != null) {
            TextButton(onClick = onRetry) {
                Text(stringResource(Res.string.retry))
            }
        }
    }
}
