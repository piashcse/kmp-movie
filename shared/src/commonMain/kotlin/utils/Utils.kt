package utils

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
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
@Composable
fun OnGridPagination(
    gridState: LazyGridState,
    buffer: Int = 5,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            if (totalItems == 0) return@derivedStateOf false
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem >= totalItems - buffer
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }
}