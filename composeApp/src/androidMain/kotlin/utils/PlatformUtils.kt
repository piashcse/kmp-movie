package utils

import android.os.SystemClock

actual fun getCurrentTimestamp(): Long {
    return SystemClock.elapsedRealtime()
}