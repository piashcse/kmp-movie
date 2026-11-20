package theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

/**
 * Material You dynamic colors on platforms that support them (Android 12+).
 * Returns null when the platform should use the app's static palette.
 */
@Composable
expect fun dynamicColorScheme(darkTheme: Boolean): ColorScheme?
