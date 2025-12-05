package ui.screens.tv_series.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class TvSeriesDetailViewModel(private val repo: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(TvSeriesDetailUiState())
    val uiState: StateFlow<TvSeriesDetailUiState> get() = _uiState.asStateFlow()

    fun fetchTvSeriesDetails(seriesId: Int) {
        viewModelScope.launch {
            launch { fetchTvSeriesDetail(seriesId) }
            launch { fetchRecommendedTvSeries(seriesId) }
            launch { fetchCreditTvSeries(seriesId) }
        }
    }

    private suspend fun fetchTvSeriesDetail(seriesId: Int) {
        repo.tvSeriesDetail(seriesId).collect { result ->
            handleStateUpdate(result) { state, data -> 
                state.copy(tvSeriesDetail = data) 
            }
        }
    }

    private suspend fun fetchRecommendedTvSeries(seriesId: Int) {
        repo.recommendedTvSeries(seriesId).collect { result ->
            handleStateUpdate(result) { state, data -> 
                state.copy(recommendedTvSeries = data.orEmpty()) 
            }
        }
    }

    private suspend fun fetchCreditTvSeries(seriesId: Int) {
        repo.creditTvSeries(seriesId).collect { result ->
            handleStateUpdate(result) { state, data -> 
                state.copy(creditTvSeries = data) 
            }
        }
    }

    private fun <T> handleStateUpdate(
        result: UiState<T>,
        stateUpdater: (TvSeriesDetailUiState, T?) -> TvSeriesDetailUiState
    ) {
        _uiState.update { currentState ->
            when (result) {
                is UiState.Loading -> currentState.copy(isLoading = true)
                is UiState.Success -> stateUpdater(
                    currentState,
                    result.data
                ).copy(isLoading = false)
                is UiState.Error -> currentState.copy(
                    isLoading = false,
                    errorMessage = result.exception.message
                )
            }
        }
    }
}