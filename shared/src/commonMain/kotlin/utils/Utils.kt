package utils

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import utils.network.UiState

class Paginator<T>(
    private val scope: CoroutineScope,
    private val initialKey: Int = 1,
    private val incrementBy: Int = 1,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Int) -> Flow<UiState<List<T>>>,
    private val onError: suspend (Throwable) -> Unit,
    private val onSuccess: suspend (items: List<T>, newKey: Int) -> Unit
) {
    private var currentKey = initialKey
    private var loadJob: Job? = null
    private var endReached = false

    /** Cancels an in-flight request so stale pages can't be appended after a filter change. */
    fun reset() {
        loadJob?.cancel()
        loadJob = null
        currentKey = initialKey
        endReached = false
    }

    fun loadNextItems() {
        if (loadJob?.isActive == true || endReached) return

        loadJob = scope.launch {
            onLoadUpdated(true)
            try {
                onRequest(currentKey).collect { result ->
                    when (result) {
                        is UiState.Loading -> Unit
                        is UiState.Success -> {
                            val items = result.data.orEmpty()
                            if (items.isEmpty()) {
                                // TMDB returns an empty page once the last page is exceeded.
                                endReached = true
                            } else {
                                val nextKey = currentKey + incrementBy
                                onSuccess(items, nextKey)
                                currentKey = nextKey
                            }
                        }

                        is UiState.Error -> onError(result.exception)
                    }
                }
            } finally {
                onLoadUpdated(false)
            }
        }
    }
}

/**
 * Requests the next page when the last visible item gets within [buffer] of the end.
 *
 * Watching the (total, lastVisible) pair instead of a single Boolean means appending a page
 * while the user is still near the end triggers the next load instead of stalling.
 */
@Composable
fun OnGridPagination(
    gridState: LazyGridState,
    buffer: Int = 5,
    onLoadMore: () -> Unit,
) {
    val currentOnLoadMore by rememberUpdatedState(onLoadMore)

    LaunchedEffect(gridState, buffer) {
        snapshotFlow {
            val layoutInfo = gridState.layoutInfo
            layoutInfo.totalItemsCount to (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1)
        }
            .distinctUntilChanged()
            .collect { (totalItems, lastVisibleItem) ->
                if (totalItems > 0 && lastVisibleItem >= totalItems - buffer) {
                    currentOnLoadMore()
                }
            }
    }
}
