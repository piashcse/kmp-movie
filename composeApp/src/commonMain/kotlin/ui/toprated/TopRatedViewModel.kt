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
import utils.network.DataState

class TopRatedViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _topRatedMovieResponse = MutableStateFlow<DataState<List<MovieItem>>>(DataState.Loading)
    val topRatedMovieResponse = _topRatedMovieResponse.asStateFlow()
    init {
        topRated(1)
    }
    fun topRated(page: Int) {
        viewModelScope.launch {
            repo.topRatedMovie(page).onEach {
                _topRatedMovieResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}