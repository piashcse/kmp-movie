package ui.component

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import ui.component.base.BaseColumn
import ui.screens.base.PagedListUiState
import utils.OnGridPagination

/**
 * Paginated media grid used by every list screen.
 */
@Composable
fun <T> GenericListScreen(
    uiState: StateFlow<PagedListUiState<T>>,
    loadItems: () -> Unit,
    getImagePath: (T) -> String?,
    getItemId: (T) -> Int,
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 250.dp,
    additionalContent: @Composable (T) -> Unit = {},
) {
    val state by uiState.collectAsState()
    // Saveable so the scroll position survives navigating to a detail screen and back.
    val gridState = rememberSaveable(saver = LazyGridState.Saver) { LazyGridState() }
    val items = state.items

    LaunchedEffect(Unit) {
        // Re-entering a tab must not trigger another page load when the ViewModel already
        // holds data (previously every recomposition of the screen fetched the next page).
        if (items.isEmpty()) {
            loadItems()
        }
    }

    // Only show the full-screen spinner before the first page arrives; appending more
    // pages must not replace the grid the user is scrolling.
    BaseColumn(
        loading = state.isLoading && items.isEmpty(),
        errorMessage = state.errorMessage,
        modifier = modifier,
    ) {
        MediaGrid(
            items = items,
            gridState = gridState,
            getImagePath = getImagePath,
            getItemId = getItemId,
            onClick = onNavigateToDetail,
            imageHeight = imageHeight,
            additionalContent = additionalContent,
        )

        OnGridPagination(gridState = gridState) {
            loadItems()
        }
    }
}
