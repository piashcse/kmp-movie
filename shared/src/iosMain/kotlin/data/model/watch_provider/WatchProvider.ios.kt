package data.model.watch_provider

// The iOS platform bindings do not expose the current locale's region directly, so the UI falls
// back to the US catalogue and then the first region that has providers.
actual fun deviceRegionCode(): String? = null
