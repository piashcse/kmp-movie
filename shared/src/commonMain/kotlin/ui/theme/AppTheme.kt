package ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

/**
 * Convenience accessors for the Material 3 theme. Kept as a single object so screens read
 * `AppTheme.colors` consistently instead of mixing Material 2 and Material 3 APIs.
 */
object AppTheme {
    val colors: ColorScheme
        @Composable get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable get() = MaterialTheme.shapes
}
