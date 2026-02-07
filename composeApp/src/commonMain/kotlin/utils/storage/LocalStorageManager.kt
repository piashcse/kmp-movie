package utils.storage

import com.russhwolf.settings.Settings
import data.model.local.FavoriteItem
import data.model.local.MediaType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class LocalStorageManager(
    private val settings: Settings,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        private const val FAVORITES_KEY = "favorites_list"

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


}