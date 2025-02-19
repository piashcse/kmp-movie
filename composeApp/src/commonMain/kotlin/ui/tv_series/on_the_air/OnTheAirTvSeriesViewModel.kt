package ui.tv_series.on_the_air

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class OnTheAirTvSeriesViewModel : ViewModel() {

    private val repo = Repository() // Ensure Singleton or pass manually

    private val _uiState = MutableStateFlow(TvSeriesUiState())
    val uiState: StateFlow<TvSeriesUiState> get() = _uiState.asStateFlow()

    fun fetchOnTheAirTvSeries(page: Int) {
        viewModelScope.launch {
            repo.onTheAirTvSeries(page).collect { result ->
                handleStateUpdate(result) { state, data -> state.copy(tvSeriesList = data) }
            }
        }
    }

    private fun <T> handleStateUpdate(
        result: UiState<T>,
        stateUpdater: (TvSeriesUiState, T?) -> TvSeriesUiState
    ) {
        _uiState.update { currentState ->
            when (result) {
                is UiState.Loading -> currentState.copy(isLoading = true)
                is UiState.Success -> stateUpdater(currentState, result.data).copy(isLoading = false)
                is UiState.Error -> currentState.copy(isLoading = false, errorMessage = result.exception.message)
            }
        }
    }
}
data class TvSeriesUiState(
    val tvSeriesList: List<TvSeriesItem>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)