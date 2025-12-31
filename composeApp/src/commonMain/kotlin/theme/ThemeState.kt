package theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ui.screens.AppViewModel

/**
 * Represents the theme mode - Light, Dark, or System default
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

/**
 * Theme state that holds the current theme mode and provides methods to change it
 */
class ThemeState(
    private val appViewModel: AppViewModel? = null
) {
    private val _themeMode = mutableStateOf(ThemeMode.SYSTEM)

    val themeMode: ThemeMode
        get() = _themeMode.value

    fun setThemeMode(mode: ThemeMode) {
        appViewModel?.setThemeMode(mode)
        _themeMode.value = mode
    }

    fun toggleTheme() {
        val newMode = when (_themeMode.value) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.SYSTEM -> ThemeMode.DARK
        }
        appViewModel?.setThemeMode(newMode)
        _themeMode.value = newMode
    }
}

/**
 * CompositionLocal for providing ThemeState throughout the app
 */
val LocalThemeState = compositionLocalOf { ThemeState() }

/**
 * Composable function to remember and provide ThemeState
 */
@Composable
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
fun ProvideThemeState(
    appViewModel: AppViewModel? = null,
    content: @Composable () -> Unit
) {
    val themeState = remember(appViewModel) { ThemeState(appViewModel) }

    appViewModel?.let { viewModel ->
        val currentThemeMode by viewModel.themeMode.collectAsStateWithLifecycle()
        @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
        LaunchedEffect(currentThemeMode) {
            themeState.setThemeMode(currentThemeMode)
        }
    }

    CompositionLocalProvider(LocalThemeState provides themeState) {
        content()
    }
}

/**
 * Helper function to determine if dark theme should be used based on current theme mode
 */
@Composable
fun shouldUseDarkTheme(): Boolean {
    val themeState = LocalThemeState.current
    return when (themeState.themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
    }
}