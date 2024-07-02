package ui.home

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
    private val _nowPlayingResponse =
        MutableStateFlow<UiState<List<MovieItem>>>(UiState.Loading)
    val nowPlayingResponse get() = _nowPlayingResponse.asStateFlow()

    fun nowPlaying(page: Int) {
        viewModelScope.launch {
            repo.nowPlayingMovie(page).onEach {
                _nowPlayingResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}