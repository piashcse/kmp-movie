package ui.theme

import androidx.compose.runtime.Composable
import theme.ThemeColors

/**
 * AppTheme object to provide easy access to theme properties
 */
object AppTheme {
    val colors: androidx.compose.material3.ColorScheme
        @Composable get() = ThemeColors.current

    val typography: androidx.compose.material3.Typography
        @Composable get() = ThemeColors.typography

    val shapes: androidx.compose.material3.Shapes
        @Composable get() = ThemeColors.shapes
}