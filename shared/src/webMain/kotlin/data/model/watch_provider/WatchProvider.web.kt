package data.model.watch_provider

import kotlinx.browser.window

actual fun deviceRegionCode(): String? =
    window.navigator.language
        .substringAfterLast('-')
        .uppercase()
        .takeIf { it.length == 2 }
