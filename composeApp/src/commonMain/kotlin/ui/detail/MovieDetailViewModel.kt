package ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.moviedetail.MovieDetail
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.network.DataState

class MovieDetailViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _movieDetail = MutableStateFlow<DataState<MovieDetail>>(DataState.Loading)
    val movieDetail = _movieDetail.asStateFlow()

    fun movieDetail(movieId: Int) {
        viewModelScope.launch {
            repo.movieDetail(movieId).collectLatest {
                _movieDetail.value = it
            }
        }
    }
}