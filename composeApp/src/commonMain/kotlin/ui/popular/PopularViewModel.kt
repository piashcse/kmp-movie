package ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.network.DataState

class PopularViewModel : ViewModel() {
    private val repo = MovieRepository()
    val popularMovieResponse = MutableStateFlow<DataState<List<MovieItem>>>(DataState.Loading)

    fun nowPlaying(page: Int) {
        viewModelScope.launch {
            repo.popularMovie(page).collectLatest {
                popularMovieResponse.value = it
            }
        }
    }
}