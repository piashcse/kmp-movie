package theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Extension properties for easier access to theme colors
 */
val ColorScheme.floatingActionBackground: Color
    @Composable get() = FloatingActionBackground

/**
 * Helper function to get the current theme colors
 */
object ThemeColors {
    val current: ColorScheme
        @Composable get() = MaterialTheme.colorScheme

    val typography: androidx.compose.material3.Typography
        @Composable get() = MaterialTheme.typography

    val shapes: androidx.compose.material3.Shapes
        @Composable get() = MaterialTheme.shapes
}