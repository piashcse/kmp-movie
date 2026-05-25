package utils

import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
private fun jsDateNow(): Double = js("Date.now()")

actual fun getCurrentTimestamp(): Long {
    return jsDateNow().toLong()
}
