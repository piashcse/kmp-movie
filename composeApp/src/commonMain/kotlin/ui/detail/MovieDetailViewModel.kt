package ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.model.artist.Artist
import data.model.moviedetail.MovieDetail
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import utils.network.UiState

class MovieDetailViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _movieDetail = MutableStateFlow<UiState<MovieDetail>>(UiState.Loading)
    val movieDetail get() = _movieDetail.asStateFlow()

    private val _recommendedMovie = MutableStateFlow<UiState<List<MovieItem>>>(UiState.Loading)
    val recommendedMovie get() = _recommendedMovie.asStateFlow()
    private val _movieCredit = MutableStateFlow<UiState<Artist>>(UiState.Loading)
    val movieCredit get() = _movieCredit.asStateFlow()

    fun movieDetail(movieId: Int) {
        viewModelScope.launch {
            repo.movieDetail(movieId).onEach {
                _movieDetail.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun recommendedMovie(movieId: Int) {
        viewModelScope.launch {
            repo.recommendedMovie(movieId).onEach {
                _recommendedMovie.value = it
            }.launchIn(viewModelScope)
        }
    }
    fun movieCredit(movieId: Int) {
        viewModelScope.launch {
            repo.movieCredit(movieId).onEach {
                _movieCredit.value = it
            }.launchIn(viewModelScope)
        }
    }
}