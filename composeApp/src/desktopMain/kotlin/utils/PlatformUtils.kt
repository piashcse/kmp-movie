package utils

import kotlin.system.getTimeMillis

actual fun getCurrentTimestamp(): Long {
    return getTimeMillis()
}