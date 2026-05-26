package utils

@OptIn(ExperimentalWasmJsInterop::class)
private fun jsDateNow(): Double = js("Date.now()")

actual fun getCurrentTimestamp(): Long {
    return jsDateNow().toLong()
}
