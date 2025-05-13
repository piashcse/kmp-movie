package ui.tv_series.top_rated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import utils.Paginator
import utils.TvSeriesUiState

class TopRatedTvSeriesViewModel(private val repo: Repository = Repository()) : ViewModel() {

    private val _uiState = MutableStateFlow(TvSeriesUiState())
    val uiState: StateFlow<TvSeriesUiState> get() = _uiState.asStateFlow()

    private val paginator = Paginator<TvSeriesItem>(
        scope = viewModelScope,
        initialKey = 1,
        incrementBy = 1,
        onLoadUpdated = { isLoading ->
            _uiState.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextKey ->
            repo.topRatedTvSeries(nextKey)
        },
        onError = { throwable ->
            _uiState.update { it.copy(errorMessage = throwable.message) }
        },
        onSuccess = { items, _ ->
            _uiState.update { current ->
                current.copy(tvSeriesList = current.tvSeriesList.orEmpty() + items)
            }
        }
    )

    fun loadTopRatedTvSeries() {
        paginator.loadNextItems()
    }
}