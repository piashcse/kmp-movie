package ui.movie.now_playing

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

class NowPlayingViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _nowPlayingResponse = MutableStateFlow<List<MovieItem>>(arrayListOf())
    val nowPlayingResponse get() = _nowPlayingResponse.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun nowPlaying(page: Int) {
        viewModelScope.launch {
            repo.nowPlayingMovie(page).onEach {
                when (it) {
                    is UiState.Loading -> {
                        _isLoading.value = true
                    }

                    is UiState.Success -> {
                        _nowPlayingResponse.value = it.data
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