package ui.screens.tv_series.airing_today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ui.screens.tv_series.TvSeriesUiState
import utils.Paginator

class AiringTodayTvSeriesViewModel(private val repo: Repository) : ViewModel() {

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
            repo.airingTodayTvSeries(nextKey)
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

    fun loadAiringTodayTvSeries() {
        paginator.loadNextItems()
    }
}