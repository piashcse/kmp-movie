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
import utils.network.DataState

class PopularViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _popularMovieResponse = MutableStateFlow<DataState<List<MovieItem>>>(DataState.Loading)
    val popularMovieResponse = _popularMovieResponse.asStateFlow()
    init {
        popularMovie(1)
    }
    fun popularMovie(page: Int) {
        viewModelScope.launch {
            repo.popularMovie(page).onEach {
                _popularMovieResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}