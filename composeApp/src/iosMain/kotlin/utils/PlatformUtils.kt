package utils

import kotlinx.cinterop.useContents
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun getCurrentTimestamp(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}