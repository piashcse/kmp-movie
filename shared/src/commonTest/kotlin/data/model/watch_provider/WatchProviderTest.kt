package data.model.watch_provider

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WatchProviderTest {

    private fun provider(id: Int, name: String) = WatchProvider(providerId = id, providerName = name)

    @Test
    fun `requested region wins`() {
        val providers = WatchProviders(
            results = mapOf(
                "US" to WatchProviderRegion(flatrate = listOf(provider(1, "US"))),
                "GB" to WatchProviderRegion(flatrate = listOf(provider(2, "GB"))),
            ),
        )

        assertEquals("GB", providers.regionFor("gb")?.providers?.single()?.providerName)
    }

    @Test
    fun `US is the fallback and empty regions are skipped`() {
        val providers = WatchProviders(
            results = mapOf(
                "GB" to WatchProviderRegion(),
                "US" to WatchProviderRegion(rent = listOf(provider(3, "US"))),
                "DE" to WatchProviderRegion(flatrate = listOf(provider(4, "DE"))),
            ),
        )

        assertEquals("US", providers.regionFor("GB")?.providers?.single()?.providerName)
    }

    @Test
    fun `first region with providers is used when US is missing`() {
        val providers = WatchProviders(
            results = mapOf(
                "DE" to WatchProviderRegion(flatrate = listOf(provider(4, "DE"))),
            ),
        )

        assertEquals("DE", providers.regionFor(null)?.providers?.single()?.providerName)
    }

    @Test
    fun `provider lists are merged without duplicates`() {
        val region = WatchProviderRegion(
            flatrate = listOf(provider(1, "Netflix")),
            rent = listOf(provider(1, "Netflix"), provider(2, "Apple TV")),
            buy = listOf(provider(2, "Apple TV"), provider(3, "Prime Video")),
        )

        assertEquals(listOf(1, 2, 3), region.providers.map { it.providerId })
    }

    @Test
    fun `no providers means no selected region`() {
        assertNull(WatchProviders(results = mapOf("US" to WatchProviderRegion())).regionFor("US"))
    }
}
