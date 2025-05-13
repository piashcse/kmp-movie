package ui.movie.now_playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import utils.MovieUiState
import utils.Paginator

class NowPlayingViewModel(private val repo: Repository = Repository()) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val paginator = Paginator(
        scope = viewModelScope,
        initialKey = 1,
        incrementBy = 1,
        onLoadUpdated = { isLoading ->
            _uiState.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { page ->
            repo.nowPlayingMovie(page)
        },
        onError = { throwable ->
            _uiState.update { it.copy(errorMessage = throwable.message) }
        },
        onSuccess = { items, _ ->
            _uiState.update { currentState ->
                currentState.copy(
                    movieList = currentState.movieList.orEmpty() + items
                )
            }
        }
    )

    fun loadNowPlayingMovies() = paginator.loadNextItems()
}