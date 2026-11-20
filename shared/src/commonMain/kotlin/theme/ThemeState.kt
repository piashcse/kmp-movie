package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import ui.screens.AppViewModel

/**
 * Represents the theme mode - Light, Dark, or System default.
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM,
}

/** Current theme mode, backed by [AppViewModel] and persisted through the repository. */
val LocalThemeMode = compositionLocalOf { ThemeMode.SYSTEM }

/** Setter for the theme mode. */
val LocalSetThemeMode = staticCompositionLocalOf<(ThemeMode) -> Unit> { {} }

@Composable
fun ProvideThemeState(
    appViewModel: AppViewModel,
    content: @Composable () -> Unit,
) {
    val themeMode by appViewModel.themeMode.collectAsState()

    CompositionLocalProvider(
        LocalThemeMode provides themeMode,
        LocalSetThemeMode provides appViewModel::setThemeMode,
    ) {
        content()
    }
}

@Composable
fun shouldUseDarkTheme(): Boolean = when (LocalThemeMode.current) {
    ThemeMode.LIGHT -> false
    ThemeMode.DARK -> true
    ThemeMode.SYSTEM -> isSystemInDarkTheme()
}
