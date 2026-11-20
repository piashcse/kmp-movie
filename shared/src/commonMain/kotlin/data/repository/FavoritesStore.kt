package data.repository

import data.model.local.FavoriteItem
import data.model.local.MediaType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import utils.storage.LocalStorageManager

/**
 * Single source of truth for favorites.
 *
 * Previously every card decoded the whole favorites JSON from storage to decide whether
 * it was favorited, and each screen kept its own copy of the state, so the lists could
 * drift apart. The store loads once, keeps an in-memory [StateFlow] and writes changes
 * back sequentially.
 */
class FavoritesStore(
    private val storage: LocalStorageManager,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
) {
    private val writeMutex = Mutex()

    private val _favorites = MutableStateFlow<List<FavoriteItem>>(emptyList())
    val favorites: StateFlow<List<FavoriteItem>> = _favorites.asStateFlow()

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> = _isLoaded.asStateFlow()

    init {
        scope.launch {
            writeMutex.withLock {
                _favorites.value = storage.getFavorites()
                _isLoaded.value = true
            }
        }
    }

    fun isFavorite(id: Int, mediaType: MediaType): Boolean =
        _favorites.value.any { it.id == id && it.mediaType == mediaType }

    fun toggle(item: FavoriteItem) {
        _favorites.update { current ->
            val exists = current.any { it.id == item.id && it.mediaType == item.mediaType }
            if (exists) {
                current.filterNot { it.id == item.id && it.mediaType == item.mediaType }
            } else {
                current + item
            }
        }
        persist()
    }

    fun remove(id: Int, mediaType: MediaType) {
        _favorites.update { current ->
            current.filterNot { it.id == id && it.mediaType == mediaType }
        }
        persist()
    }

    private fun persist() {
        scope.launch {
            writeMutex.withLock {
                storage.saveFavorites(_favorites.value)
            }
        }
    }
}
