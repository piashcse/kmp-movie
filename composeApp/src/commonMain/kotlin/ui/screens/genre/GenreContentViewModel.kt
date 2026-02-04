package ui.screens.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.BaseModel
import data.model.MovieItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import utils.network.UiState

class GenreContentViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GenreUiState())
    val uiState: StateFlow<GenreUiState> = _uiState

    private var currentGenreId: Int = 0
    private var currentGenreName: String = ""

    fun setGenre(genreId: Int, genreName: String) {
        currentGenreId = genreId
        currentGenreName = genreName
    }

    fun loadMoviesByGenre() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val result = repository.getMoviesByGenre(currentGenreId, _uiState.value.currentPage + 1)

                result.collect { state ->
                    when (state) {
                        is utils.network.UiState.Loading -> {
                            // Already handled by isLoading flag
                        }
                        is utils.network.UiState.Success -> {
                            val newPage = _uiState.value.currentPage + 1
                            val updatedMovies = _uiState.value.movieList.toMutableList()
                            updatedMovies.addAll(state.data ?: emptyList())

                            _uiState.value = _uiState.value.copy(
                                movieList = updatedMovies,
                                currentPage = newPage,
                                isLoading = false
                            )
                        }
                        is utils.network.UiState.Error -> {
                            _uiState.value = _uiState.value.copy(
                                errorMessage = state.exception.message,
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
            }
        }
    }
}

data class GenreUiState(
    val movieList: List<MovieItem> = emptyList(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)