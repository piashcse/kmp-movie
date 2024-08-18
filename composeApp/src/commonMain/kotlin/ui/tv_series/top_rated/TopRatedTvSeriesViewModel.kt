package ui.tv_series.top_rated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.TvItem
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import utils.network.UiState


class TopRatedTvSeriesViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _topRatedTvSeriesResponse = MutableStateFlow<List<TvItem>>(arrayListOf())
    val topRatedTvSeriesResponse get() = _topRatedTvSeriesResponse.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun upComingTvSeries(page: Int) {
        viewModelScope.launch {
            repo.upcomingTvSeries(page).onEach {
                when (it) {
                    is UiState.Loading -> {
                        _isLoading.value = true
                    }

                    is UiState.Success -> {
                        _topRatedTvSeriesResponse.value = it.data
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