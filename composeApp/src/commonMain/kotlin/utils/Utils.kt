package utils

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.artist.Artist
import data.model.celebrities.Celebrity
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import utils.network.UiState


data class MovieUiState(
    val movieList: List<MovieItem>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class TvSeriesUiState(
    val tvSeriesList: List<TvSeriesItem>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CelebrityUiState(
    val celebrityList: List<Celebrity>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val recommendedMovies: List<MovieItem> = emptyList(),
    val movieCredit: Artist? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class TvSeriesDetailUiState(
    val isLoading: Boolean = false,
    val tvSeriesDetail: TvSeriesDetail? = null,
    val recommendedTvSeries: List<TvSeriesItem> = emptyList(),
    val creditTvSeries: Credit? = null,
    val errorMessage: String? = null
)

class Paginator<T>(
    private val scope: CoroutineScope,
    private val initialKey: Int = 1,
    private val incrementBy: Int = 1,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Int) -> Flow<UiState<List<T>>>,
    private val onError: suspend (Throwable) -> Unit,
    private val onSuccess: suspend (items: List<T>, newKey: Int) -> Unit
) {
    private var isMakingRequest = false
    private var currentKey = initialKey
    private var hasError = false  // NEW FLAG

    fun reset() {
        currentKey = initialKey
        hasError = false
    }

    fun loadNextItems() {
        if (isMakingRequest || hasError) return  // Prevent further loading if error occurred

        isMakingRequest = true
        scope.launch {
            onLoadUpdated(true)

            val resultFlow = onRequest(currentKey)

            resultFlow.collect { result ->
                when (result) {
                    is UiState.Loading -> Unit
                    is UiState.Success -> {
                        val items = result.data.orEmpty()
                        val nextKey = currentKey + incrementBy
                        onSuccess(items, nextKey)
                        currentKey = nextKey
                    }
                    is UiState.Error -> {
                        hasError = true  // STOP FURTHER PAGINATION
                        onError(result.exception)
                    }
                }
            }

            onLoadUpdated(false)
            isMakingRequest = false
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