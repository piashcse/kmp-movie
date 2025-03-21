package ui.movie.now_playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class NowPlayingViewModel : ViewModel() {

    private val repo = Repository() // Ensure Singleton or pass manually

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> get() = _uiState.asStateFlow()

    fun fetchNowPlayingMovie(page: Int) {
        viewModelScope.launch {
            repo.nowPlayingMovie(page).collect { result ->
                handleStateUpdate(result) { state, data -> state.copy(movieList = data) }
            }
        }
    }

    private fun <T> handleStateUpdate(
        result: UiState<T>,
        stateUpdater: (MovieUiState, T?) -> MovieUiState
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

data class MovieUiState(
    val movieList: List<MovieItem>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)