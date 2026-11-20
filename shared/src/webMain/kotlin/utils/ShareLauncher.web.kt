package utils

import androidx.compose.runtime.Composable

@Composable
actual fun rememberShareLauncher(): ShareLauncher = rememberClipboardShareLauncher()
