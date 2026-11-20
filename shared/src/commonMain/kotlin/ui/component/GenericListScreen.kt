package ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.end_of_list
import kmp_movie.composeapp.generated.resources.no_results
import kmp_movie.composeapp.generated.resources.retry
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import ui.component.base.BaseColumn
import ui.component.skeleton.MediaGridSkeleton
import theme.Spacing
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
    getContentDescription: (T) -> String,
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
        errorMessage = state.errorMessage.takeIf { items.isEmpty() },
        onRetry = loadItems,
        modifier = modifier,
        loadingContent = { MediaGridSkeleton(imageHeight = imageHeight) },
    ) {
        if (items.isEmpty()) {
            EmptyState(stringResource(Res.string.no_results))
        } else {
            MediaGrid(
                items = items,
                gridState = gridState,
                getImagePath = getImagePath,
                getItemId = getItemId,
                getContentDescription = getContentDescription,
                onClick = onNavigateToDetail,
                imageHeight = imageHeight,
                additionalContent = additionalContent,
                footer = {
                    when {
                        state.errorMessage != null -> LoadMoreErrorFooter(
                            message = state.errorMessage.orEmpty(),
                            onRetry = loadItems,
                        )

                        state.isLoading -> LoadingFooter()
                        state.endReached -> EndOfListFooter()
                    }
                },
            )

            OnGridPagination(gridState = gridState) {
                loadItems()
            }
        }
    }
}

@Composable
private fun LoadingFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.lg),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            strokeWidth = 2.dp,
        )
    }
}

@Composable
private fun LoadMoreErrorFooter(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
        )
        TextButton(onClick = onRetry) {
            Text(stringResource(Res.string.retry))
        }
    }
}

@Composable
private fun EndOfListFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.lg),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.end_of_list),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
