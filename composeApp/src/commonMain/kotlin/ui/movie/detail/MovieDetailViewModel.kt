package ui.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.model.artist.Artist
import data.model.movie_detail.MovieDetail
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import utils.network.UiState

class MovieDetailViewModel : ViewModel() {
    private val repo = MovieRepository()

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail get() = _movieDetail.asStateFlow()

    private val _recommendedMovie = MutableStateFlow<List<MovieItem>>(arrayListOf())
    val recommendedMovie get() = _recommendedMovie.asStateFlow()

    private val _movieCredit = MutableStateFlow<Artist?>(null)
    val movieCredit get() = _movieCredit.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun movieDetail(movieId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                repo.movieDetail(movieId).onEach {
                    when (it) {
                        is UiState.Loading -> {
                            _isLoading.value = true
                        }

                        is UiState.Success -> {
                            _movieDetail.value = it.data
                            _isLoading.value = false
                        }

                        is UiState.Error -> {
                            _isLoading.value = false
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun recommendedMovie(movieId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                repo.recommendedMovie(movieId).onEach {
                    when (it) {
                        is UiState.Loading -> {
                            _isLoading.value = true
                        }

                        is UiState.Success -> {
                            _recommendedMovie.value = it.data
                            _isLoading.value = false
                        }

                        is UiState.Error -> {
                            _isLoading.value = false
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
    fun movieCredit(movieId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                repo.movieCredit(movieId).onEach {
                    when (it) {
                        is UiState.Loading -> {
                            _isLoading.value = true
                        }

                        is UiState.Success -> {
                            _movieCredit.value = it.data
                            _isLoading.value = false
                        }

                        is UiState.Error -> {
                            _isLoading.value = false
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}