package data.model.watch_provider

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchProviders(
    val results: Map<String, WatchProviderRegion> = emptyMap(),
)

@Serializable
data class WatchProviderRegion(
    val link: String? = null,
    val flatrate: List<WatchProvider> = emptyList(),
    val rent: List<WatchProvider> = emptyList(),
    val buy: List<WatchProvider> = emptyList(),
) {
    /** Streaming services first, then rental and purchase options, without duplicates. */
    val providers: List<WatchProvider>
        get() = (flatrate + rent + buy).distinctBy { it.providerId }

    val hasProviders: Boolean
        get() = flatrate.isNotEmpty() || rent.isNotEmpty() || buy.isNotEmpty()
}

/** Region detection differs per platform; returns an ISO 3166-1 alpha-2 code when known. */
expect fun deviceRegionCode(): String?

/**
 * Picks the requested region, then the US, then the first region that actually has providers.
 */
fun WatchProviders.regionFor(code: String?): WatchProviderRegion? {
    val preferred = code
        ?.trim()
        ?.uppercase()
        ?.let { results[it] }
        ?.takeIf { it.hasProviders }
    return preferred
        ?: results["US"]?.takeIf { it.hasProviders }
        ?: results.values.firstOrNull { it.hasProviders }
}

@Serializable
data class WatchProvider(
    @SerialName("provider_id") val providerId: Int = 0,
    @SerialName("provider_name") val providerName: String = "",
    @SerialName("logo_path") val logoPath: String? = null,
    @SerialName("display_priority") val displayPriority: Int = 0,
)
