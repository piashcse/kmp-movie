package ui.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import utils.network.UiState

class TopRatedViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _topRatedMovieResponse =
        MutableStateFlow<UiState<List<MovieItem>>>(UiState.Loading)
    val topRatedMovieResponse get() = _topRatedMovieResponse.asStateFlow()

    fun topRated(page: Int) {
        viewModelScope.launch {
            repo.topRatedMovie(page).onEach {
                _topRatedMovieResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}