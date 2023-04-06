package ui.home

import data.remote.NowPlayingMovieApiImpl
import data.remote.model.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import utils.network.DataState

class NowPlayingViewModel {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val nowPlayingApi = NowPlayingMovieApiImpl()
    val nowPlayingResponse = MutableStateFlow<DataState<List<MovieItem>>?>(DataState.Loading)

    init {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val movieList = nowPlayingApi.nowPlayingMovieList(1)
                nowPlayingResponse.value = DataState.Success( movieList.results)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}