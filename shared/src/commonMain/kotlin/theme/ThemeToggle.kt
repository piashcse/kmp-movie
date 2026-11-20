package theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.LightMode
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
            imageVector = when (themeMode) {
                ThemeMode.LIGHT -> Icons.Default.LightMode
                ThemeMode.DARK -> Icons.Default.Brightness4
                ThemeMode.SYSTEM -> Icons.Default.BrightnessAuto
            },
            contentDescription = "Theme Mode Toggle",
            tint = MaterialTheme.colorScheme.primary,
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        DropdownMenuItem(
            text = { Text("Light Mode") },
            onClick = {
                setThemeMode(ThemeMode.LIGHT)
                expanded = false
            },
            leadingIcon = { Icon(Icons.Default.LightMode, contentDescription = null) },
        )
        DropdownMenuItem(
            text = { Text("Dark Mode") },
            onClick = {
                setThemeMode(ThemeMode.DARK)
                expanded = false
            },
            leadingIcon = { Icon(Icons.Default.Brightness4, contentDescription = null) },
        )
        DropdownMenuItem(
            text = { Text("System Default") },
            onClick = {
                setThemeMode(ThemeMode.SYSTEM)
                expanded = false
            },
            leadingIcon = { Icon(Icons.Default.BrightnessAuto, contentDescription = null) },
        )
    }

    if (showText) {
        Text(
            text = when (themeMode) {
                ThemeMode.LIGHT -> "Light"
                ThemeMode.DARK -> "Dark"
                ThemeMode.SYSTEM -> "System"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
