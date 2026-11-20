package ui.screens.movie.detail

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

class MovieDetailViewModel(private val repo: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> get() = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var currentMovieId: Int? = null

    fun fetchMovieDetails(movieId: Int) {
        if (currentMovieId == movieId && _uiState.value.movieDetail != null) return

        currentMovieId = movieId
        fetchJob?.cancel()
        _uiState.value = MovieDetailUiState()
        fetchJob = viewModelScope.launch {
            launch { updateMovieDetail(movieId) }
            launch { updateRecommendedMovies(movieId) }
            launch { updateMovieCredit(movieId) }
        }
    }

    private suspend fun updateMovieDetail(movieId: Int) {
        repo.movieDetail(movieId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(movieDetail = data) }
        }
    }

    private suspend fun updateRecommendedMovies(movieId: Int) {
        repo.recommendedMovie(movieId).collect { result ->
            handleStateUpdate(result) { state, data ->
                state.copy(recommendedMovies = data.orEmpty())
            }
        }
    }

    private suspend fun updateMovieCredit(movieId: Int) {
        repo.movieCredit(movieId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(movieCredit = data) }
        }
    }

    private fun <T> handleStateUpdate(
        result: UiState<T>,
        stateUpdater: (MovieDetailUiState, T?) -> MovieDetailUiState,
    ) {
        _uiState.update { currentState ->
            when (result) {
                is UiState.Loading -> currentState.copy(isLoading = true)
                is UiState.Success -> stateUpdater(
                    currentState,
                    result.data,
                ).copy(isLoading = false)

                is UiState.Error -> currentState.copy(
                    isLoading = false,
                    errorMessage = result.exception.message,
                )
            }
        }
    }
}
