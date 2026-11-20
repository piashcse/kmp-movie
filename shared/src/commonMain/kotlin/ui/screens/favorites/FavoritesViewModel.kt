package ui.screens.favorites

import androidx.lifecycle.ViewModel
import data.model.local.FavoriteItem
import data.repository.FavoritesStore
import kotlinx.coroutines.flow.StateFlow

class FavoritesViewModel(
    private val store: FavoritesStore,
) : ViewModel() {
    val favorites: StateFlow<List<FavoriteItem>> = store.favorites
    val isLoaded: StateFlow<Boolean> = store.isLoaded

    fun remove(item: FavoriteItem) = store.remove(item.id, item.mediaType)
}
