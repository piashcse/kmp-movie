package ui.celebrities.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.celebrities.Celebrity
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class TrendingCelebritiesViewModel : ViewModel() {

    private val repo = Repository() // Ensure Singleton or pass manually

    private val _uiState = MutableStateFlow(CelebrityUiState())
    val uiState: StateFlow<CelebrityUiState> get() = _uiState.asStateFlow()

    fun fetchTrendingCelebrities(page: Int) {
        viewModelScope.launch {
            repo.trendingCelebrities(page).collect { result ->
                handleStateUpdate(result) { state, data -> state.copy(celebrityList = data) }
            }
        }
    }

    private fun <T> handleStateUpdate(
        result: UiState<T>,
        stateUpdater: (CelebrityUiState, T?) -> CelebrityUiState
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

data class CelebrityUiState(
    val celebrityList: List<Celebrity>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)