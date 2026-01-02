package theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThemeModeToggle(
    modifier: Modifier = Modifier,
    showText: Boolean = false
) {
    val themeState = LocalThemeState.current
    var expanded by remember { mutableStateOf(false) }
    
    IconButton(
        onClick = { expanded = true },
        modifier = modifier
    ) {
        Icon(
            imageVector = when (themeState.themeMode) {
                ThemeMode.LIGHT -> Icons.Default.LightMode
                ThemeMode.DARK -> Icons.Default.Brightness4
                ThemeMode.SYSTEM -> Icons.Default.BrightnessAuto
            },
            contentDescription = "Theme Mode Toggle",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Light Mode") },
            onClick = {
                themeState.setThemeMode(ThemeMode.LIGHT)
                expanded = false
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LightMode,
                    contentDescription = null
                )
            }
        )
        DropdownMenuItem(
            text = { Text("Dark Mode") },
            onClick = {
                themeState.setThemeMode(ThemeMode.DARK)
                expanded = false
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Brightness4,
                    contentDescription = null
                )
            }
        )
        DropdownMenuItem(
            text = { Text("System Default") },
            onClick = {
                themeState.setThemeMode(ThemeMode.SYSTEM)
                expanded = false
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.BrightnessAuto,
                    contentDescription = null
                )
            }
        )
    }
    
    if (showText) {
        Text(
            text = when (themeState.themeMode) {
                ThemeMode.LIGHT -> "Light"
                ThemeMode.DARK -> "Dark"
                ThemeMode.SYSTEM -> "System"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ThemeModePreview() {
    val themeState = LocalThemeState.current
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Current Theme: ${themeState.themeMode.name}",
            style = MaterialTheme.typography.headlineSmall
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Theme Mode: ")
            Spacer(modifier = Modifier.width(8.dp))
            ThemeModeToggle(showText = true)
        }
        
        Button(onClick = { themeState.toggleTheme() }) {
            Text("Toggle Theme")
        }
    }
}