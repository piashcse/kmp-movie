package navigation

import androidx.compose.runtime.Composable

@Composable
actual fun rememberDeepLinkController(): DeepLinkController = NoOpDeepLinkController
