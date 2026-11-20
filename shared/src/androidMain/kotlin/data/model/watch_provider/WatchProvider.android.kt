package data.model.watch_provider

import java.util.Locale

actual fun deviceRegionCode(): String? =
    Locale.getDefault().country.takeIf { it.isNotBlank() }
