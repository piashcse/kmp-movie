package ui.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.model.artist.Artist
import data.model.movie_detail.MovieDetail
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class MovieDetailViewModel : ViewModel() {

    private val repo = Repository() // Ensure this is a Singleton or injected manually

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> get() = _uiState.asStateFlow()

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
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
                state.copy(
                    recommendedMovies = data ?: emptyList()
                )
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
        stateUpdater: (MovieDetailUiState, T?) -> MovieDetailUiState
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

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val recommendedMovies: List<MovieItem> = emptyList(),
    val movieCredit: Artist? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)