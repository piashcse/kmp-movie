package ui.screens.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.component.GenericListScreen
import ui.component.GenreChips
import ui.component.GenreItem

/**
 * Shared layout for the paginated movie/TV screens: a genre filter row above a media grid.
 * Subclasses only provide their ViewModel and state accessors.
 */
@Composable
fun <T, G : GenreItem, S> GenreFilteredListScreen(
    viewModel: GenreFilterableViewModel<T, G, S>,
    getItems: (S) -> List<T>,
    getIsLoading: (S) -> Boolean,
    getErrorMessage: (S) -> String?,
    getImagePath: (T) -> String?,
    getItemId: (T) -> Int,
    onNavigateToDetail: (Int) -> Unit,
    imageHeight: Dp = 250.dp,
) {
    val availableGenres by viewModel.availableGenres.collectAsState()
    val selectedGenreId by viewModel.selectedGenreId.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        GenreChips(
            genres = availableGenres,
            selectedGenreId = selectedGenreId,
            onGenreSelected = viewModel::selectGenre,
        )
        Box(modifier = Modifier.weight(1f)) {
            GenericListScreen(
                uiState = viewModel.uiState,
                loadItems = viewModel::loadItems,
                getItems = getItems,
                getIsLoading = getIsLoading,
                getErrorMessage = getErrorMessage,
                getImagePath = getImagePath,
                getItemId = getItemId,
                onNavigateToDetail = onNavigateToDetail,
                imageHeight = imageHeight,
            )
        }
    }
}
