package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

/**
 * Hands a link to the platform's share sheet. Platforms without a system share sheet fall back
 * to copying the link, so callers can tell the user what happened via the return value.
 */
interface ShareLauncher {
    /** Returns true when the link was copied to the clipboard instead of opening a share sheet. */
    fun share(title: String, url: String): Boolean
}

@Composable
expect fun rememberShareLauncher(): ShareLauncher

/** Clipboard fallback shared by the desktop, iOS and web implementations. */
@Suppress("DEPRECATION")
@Composable
internal fun rememberClipboardShareLauncher(): ShareLauncher {
    val clipboard = LocalClipboardManager.current
    return remember(clipboard) {
        object : ShareLauncher {
            override fun share(title: String, url: String): Boolean {
                clipboard.setText(AnnotatedString(url))
                return true
            }
        }
    }
}
