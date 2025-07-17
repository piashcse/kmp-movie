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

class TvSeriesDetailViewModel(private val repo: Repository = Repository()) : ViewModel() {

    private val _uiState = MutableStateFlow(TvSeriesDetailUiState())
    val uiState: StateFlow<TvSeriesDetailUiState> get() = _uiState.asStateFlow()

    fun fetchTvSeriesDetails(seriesId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            launch { fetchTvSeriesDetail(seriesId) }
            launch { fetchRecommendedTvSeries(seriesId) }
            launch { fetchCreditTvSeries(seriesId) }
        }
    }

    private suspend fun fetchTvSeriesDetail(seriesId: Int) {
        repo.tvSeriesDetail(seriesId).collect { result ->
            _uiState.update {
                when (result) {
                    is UiState.Loading -> it.copy(isLoading = true)
                    is UiState.Success -> it.copy(tvSeriesDetail = result.data, isLoading = false)
                    is UiState.Error -> it.copy(isLoading = false)
                }
            }
        }
    }

    private suspend fun fetchRecommendedTvSeries(seriesId: Int) {
        repo.recommendedTvSeries(seriesId).collect { result ->
            _uiState.update {
                when (result) {
                    is UiState.Loading -> it.copy(isLoading = true)
                    is UiState.Success -> it.copy(
                        recommendedTvSeries = result.data,
                        isLoading = false
                    )

                    is UiState.Error -> it.copy(isLoading = false)
                }
            }
        }
    }

    private suspend fun fetchCreditTvSeries(seriesId: Int) {
        repo.creditTvSeries(seriesId).collect { result ->
            _uiState.update {
                when (result) {
                    is UiState.Loading -> it.copy(isLoading = true)
                    is UiState.Success -> it.copy(creditTvSeries = result.data, isLoading = false)
                    is UiState.Error -> it.copy(
                        isLoading = false,
                        errorMessage = result.exception.message
                    )
                }
            }
        }
    }
}