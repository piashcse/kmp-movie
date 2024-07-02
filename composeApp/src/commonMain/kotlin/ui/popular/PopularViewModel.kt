package ui.popular

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

class PopularViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _popularMovieResponse =
        MutableStateFlow<UiState<List<MovieItem>>>(UiState.Loading)
    val popularMovieResponse get() = _popularMovieResponse.asStateFlow()

    fun popularMovie(page: Int) {
        viewModelScope.launch {
            repo.popularMovie(page).onEach {
                _popularMovieResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}