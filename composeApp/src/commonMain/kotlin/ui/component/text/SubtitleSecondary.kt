package ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import theme.subTitleSecondary

@Composable
fun SubtitleSecondary(text: String) {
    Text(
        text = text,
        style = subTitleSecondary.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
