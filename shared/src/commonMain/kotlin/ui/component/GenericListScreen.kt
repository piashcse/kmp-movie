package ui.component

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import ui.component.base.BaseColumn
import utils.OnGridPagination

/**
 * Generic list screen component that handles common list screen logic
 * Works with existing UI state structures (MovieUiState, TvSeriesUiState, etc.)
 * 
 * @param T The type of items to display
 * @param S The type of UI state
 * @param uiState StateFlow of the UI state
 * @param loadItems Function to load items
 * @param getItems Function to extract items list from UI state
 * @param getIsLoading Function to extract loading state from UI state
 * @param getErrorMessage Function to extract error message from UI state
 * @param getImagePath Function to extract image path from item
 * @param getItemId Function to extract ID from item
 * @param onNavigateToDetail Callback when an item is clicked
 * @param imageHeight Height of the image (default 250.dp)
 * @param additionalContent Optional composable to display below each item
 */
@Composable
fun <T, S> GenericListScreen(
    uiState: StateFlow<S>,
    loadItems: () -> Unit,
    getItems: (S) -> List<T>?,
    getIsLoading: (S) -> Boolean,
    getErrorMessage: (S) -> String?,
    getImagePath: (T) -> String?,
    getItemId: (T) -> Int,
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 250.dp,
    additionalContent: @Composable (T) -> Unit = {}
) {
    val state by uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        loadItems()
    }

    BaseColumn(
        loading = getIsLoading(state),
        errorMessage = getErrorMessage(state),
        modifier = modifier
    ) {
        getItems(state)?.let { items ->
            MediaGrid(
                items = items,
                gridState = gridState,
                getImagePath = getImagePath,
                getItemId = getItemId,
                onClick = onNavigateToDetail,
                imageHeight = imageHeight,
                additionalContent = additionalContent
            )
            
            OnGridPagination(gridState = gridState) {
                loadItems()
            }
        }
    }
}
