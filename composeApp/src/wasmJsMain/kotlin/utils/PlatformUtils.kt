package utils

import kotlinx.browser.window
import kotlin.js.Date

actual fun getCurrentTimestamp(): Long {
    return Date.now().toLong()
}