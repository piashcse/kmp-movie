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
            repo.movieDetail(movieId).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is UiState.Loading -> state.copy(isLoading = true)
                        is UiState.Success -> {
                            val detail = result.data
                            state.copy(
                                movieDetail = detail,
                                recommendedMovies = detail.recommendations?.results.orEmpty(),
                                movieCredit = detail.credits,
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
