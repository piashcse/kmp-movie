package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.network.DataState

class NowPlayingViewModel : ViewModel(){
    private val repo = MovieRepository()
    val nowPlayingResponse = MutableStateFlow<DataState<List<MovieItem>>?>(DataState.Loading)
    fun nowPlaying(page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.nowPlayingMovie(page).collectLatest {
                nowPlayingResponse.value = it
            }
        }
    }
}