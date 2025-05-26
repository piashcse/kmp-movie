package ui.movie.top_rated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ui.movie.MovieUiState
import utils.Paginator

class TopRatedViewModel(private val repo: Repository = Repository()) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> get() = _uiState.asStateFlow()

    private val paginator = Paginator<MovieItem>(
        scope = viewModelScope,
        initialKey = 1,
        incrementBy = 1,
        onLoadUpdated = { isLoading ->
            _uiState.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextKey ->
            repo.topRatedMovie(nextKey)
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

    fun loadTopRatedMovies() {
        paginator.loadNextItems()
    }
}