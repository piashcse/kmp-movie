package ui.screens.tv_series.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class TvSeriesDetailViewModel(private val repo: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(TvSeriesDetailUiState())
    val uiState: StateFlow<TvSeriesDetailUiState> get() = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var currentSeriesId: Int? = null

    fun fetchTvSeriesDetails(seriesId: Int) {
        if (currentSeriesId == seriesId && _uiState.value.tvSeriesDetail != null) return

        currentSeriesId = seriesId
        fetchJob?.cancel()
        _uiState.value = TvSeriesDetailUiState()
        fetchJob = viewModelScope.launch {
            repo.tvSeriesDetail(seriesId).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is UiState.Loading -> state.copy(isLoading = true)
                        is UiState.Success -> {
                            val detail = result.data
                            state.copy(
                                tvSeriesDetail = detail,
                                recommendedTvSeries = detail.recommendations?.results.orEmpty(),
                                creditTvSeries = detail.credits,
                                isLoading = false,
                            )
                        }

                        is UiState.Error -> state.copy(
                            isLoading = false,
                            errorMessage = result.exception.message,
                        )
                    }
                }
            }
        }
    }
}
