package data.repository

import com.russhwolf.settings.MapSettings
import data.model.local.FavoriteItem
import data.model.local.MediaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import utils.storage.LocalStorageManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesStoreTest {

    private val item = FavoriteItem(
        id = 42,
        mediaType = MediaType.MOVIE,
        title = "Movie",
        posterPath = "/poster.jpg",
        releaseDate = "2024-01-01",
    )

    @Test
    fun `loads, toggles and removes favorites`() = runTest {
        val storage = LocalStorageManager(
            settings = MapSettings(),
            dispatcher = UnconfinedTestDispatcher(testScheduler),
        )
        val store = FavoritesStore(storage, this)

        advanceUntilIdle()
        assertTrue(store.isLoaded.value)

        store.toggle(item)
        advanceUntilIdle()
        assertTrue(store.isFavorite(item.id, item.mediaType))
        assertEquals(listOf(item), store.favorites.value)

        store.toggle(item)
        advanceUntilIdle()
        assertFalse(store.isFavorite(item.id, item.mediaType))
        assertTrue(store.favorites.value.isEmpty())
    }

    @Test
    fun `persists favorites for a new store instance`() = runTest {
        val settings = MapSettings()
        val storage = LocalStorageManager(
            settings = settings,
            dispatcher = UnconfinedTestDispatcher(testScheduler),
        )

        FavoritesStore(storage, this).apply {
            advanceUntilIdle()
            toggle(item)
            advanceUntilIdle()
        }

        val reloaded = FavoritesStore(storage, this)
        advanceUntilIdle()
        assertEquals(listOf(item), reloaded.favorites.value)
    }
}
