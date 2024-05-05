package ui.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.network.DataState

class UpcomingViewModel : ViewModel() {
    private val repo = MovieRepository()
    val upComingMovieResponse = MutableStateFlow<DataState<List<MovieItem>>?>(DataState.Loading)

    fun upComing(page: Int) {
        viewModelScope.launch {
            repo.upComingMovie(page).collectLatest {
                upComingMovieResponse.value = it
            }
        }
    }
}