package ui.detail

import data.model.moviedetail.MovieDetail
import data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.network.DataState

class MovieDetailViewModel {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val repo = MovieRepository()
    val movieDetail = MutableStateFlow<DataState<MovieDetail>?>(DataState.Loading)

    fun movieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.movieDetail(movieId).collectLatest {
                movieDetail.value = it
            }
        }
    }
}