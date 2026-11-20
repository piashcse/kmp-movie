package theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.theme_dark
import kmp_movie.composeapp.generated.resources.theme_dark_short
import kmp_movie.composeapp.generated.resources.theme_light
import kmp_movie.composeapp.generated.resources.theme_light_short
import kmp_movie.composeapp.generated.resources.theme_mode_toggle
import kmp_movie.composeapp.generated.resources.theme_system
import kmp_movie.composeapp.generated.resources.theme_system_short
import org.jetbrains.compose.resources.stringResource

@Composable
fun ThemeModeToggle(
    modifier: Modifier = Modifier,
    showText: Boolean = false,
) {
    val themeMode = LocalThemeMode.current
    val setThemeMode = LocalSetThemeMode.current
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true },
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(Res.string.theme_mode_toggle),
            tint = MaterialTheme.colorScheme.primary,
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(Res.string.theme_light)) },
            onClick = {
                setThemeMode(ThemeMode.LIGHT)
                expanded = false
            },
        )
        DropdownMenuItem(
            text = { Text(stringResource(Res.string.theme_dark)) },
            onClick = {
                setThemeMode(ThemeMode.DARK)
                expanded = false
            },
        )
        DropdownMenuItem(
            text = { Text(stringResource(Res.string.theme_system)) },
            onClick = {
                setThemeMode(ThemeMode.SYSTEM)
                expanded = false
            },
        )
    }

    if (showText) {
        Text(
            text = when (themeMode) {
                ThemeMode.LIGHT -> stringResource(Res.string.theme_light_short)
                ThemeMode.DARK -> stringResource(Res.string.theme_dark_short)
                ThemeMode.SYSTEM -> stringResource(Res.string.theme_system_short)
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
