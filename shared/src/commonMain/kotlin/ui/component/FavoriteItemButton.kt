package ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import data.model.local.FavoriteItem
import data.repository.FavoritesStore
import org.koin.compose.koinInject

/**
 * Favorite toggle bound to a single item. Keeps the favorites flow collection in one place
 * instead of repeating it in every detail screen.
 */
@Composable
fun FavoriteItemButton(
    item: FavoriteItem,
    modifier: Modifier = Modifier,
) {
    val store = koinInject<FavoritesStore>()
    val favorites by store.favorites.collectAsState()

    FavoriteButton(
        isFavorite = favorites.any { it.id == item.id && it.mediaType == item.mediaType },
        onClick = { store.toggle(item) },
        modifier = modifier,
    )
}
