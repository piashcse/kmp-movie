package utils.storage

import com.russhwolf.settings.Settings
import data.model.local.FavoriteItem
import data.model.local.MediaType
import data.model.local.WatchlistItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class LocalStorageManager(
    private val settings: Settings,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        private const val FAVORITES_KEY = "favorites_list"
        private const val WATCHLIST_KEY = "watchlist_list"
    }

    suspend fun saveFavorites(favorites: List<FavoriteItem>) = withContext(dispatcher) {
        val serializer = ListSerializer(FavoriteItem.serializer())
        settings.putString(key = FAVORITES_KEY, value = json.encodeToString(serializer, favorites))
    }

    suspend fun getFavorites(): List<FavoriteItem> = withContext(dispatcher) {
        try {
            val serializer = ListSerializer(FavoriteItem.serializer())
            json.decodeFromString(serializer, settings.getString(FAVORITES_KEY, "[]"))
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addFavorite(favoriteItem: FavoriteItem) = withContext(dispatcher) {
        val currentFavorites = getFavorites()
        val updatedFavorites = if (!currentFavorites.any { it.id == favoriteItem.id && it.mediaType == favoriteItem.mediaType }) {
            currentFavorites + favoriteItem
        } else {
            currentFavorites
        }
        saveFavorites(updatedFavorites)
    }

    suspend fun removeFavorite(id: Int, mediaType: MediaType) = withContext(dispatcher) {
        val currentFavorites = getFavorites()
        val updatedFavorites = currentFavorites.filterNot { it.id == id && it.mediaType == mediaType }
        saveFavorites(updatedFavorites)
    }

    suspend fun isFavorite(id: Int, mediaType: MediaType): Boolean = withContext(dispatcher) {
        getFavorites().any { it.id == id && it.mediaType == mediaType }
    }

    suspend fun saveWatchlist(watchlist: List<WatchlistItem>) = withContext(dispatcher) {
        val serializer = ListSerializer(WatchlistItem.serializer())
        settings.putString(key = WATCHLIST_KEY, value = json.encodeToString(serializer, watchlist))
    }

    suspend fun getWatchlist(): List<WatchlistItem> = withContext(dispatcher) {
        try {
            val serializer = ListSerializer(WatchlistItem.serializer())
            json.decodeFromString(serializer, settings.getString(WATCHLIST_KEY, "[]"))
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addToWatchlist(watchlistItem: WatchlistItem) = withContext(dispatcher) {
        val currentWatchlist = getWatchlist()
        val updatedWatchlist = if (!currentWatchlist.any { it.id == watchlistItem.id && it.mediaType == watchlistItem.mediaType }) {
            currentWatchlist + watchlistItem
        } else {
            // Update existing item
            currentWatchlist.map { if (it.id == watchlistItem.id && it.mediaType == watchlistItem.mediaType) watchlistItem else it }
        }
        saveWatchlist(updatedWatchlist)
    }

    suspend fun removeFromWatchlist(id: Int, mediaType: MediaType) = withContext(dispatcher) {
        val currentWatchlist = getWatchlist()
        val updatedWatchlist = currentWatchlist.filterNot { it.id == id && it.mediaType == mediaType }
        saveWatchlist(updatedWatchlist)
    }

    suspend fun isInWatchlist(id: Int, mediaType: MediaType): Boolean = withContext(dispatcher) {
        getWatchlist().any { it.id == id && it.mediaType == mediaType }
    }

    suspend fun updateWatchlistStatus(id: Int, mediaType: MediaType, status: data.model.local.WatchlistStatus) = withContext(dispatcher) {
        val currentWatchlist = getWatchlist()
        val updatedWatchlist = currentWatchlist.map {
            if (it.id == id && it.mediaType == mediaType) {
                it.copy(status = status)
            } else {
                it
            }
        }
        saveWatchlist(updatedWatchlist)
    }
}