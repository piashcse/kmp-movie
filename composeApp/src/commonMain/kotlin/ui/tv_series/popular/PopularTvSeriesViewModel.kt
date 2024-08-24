package ui.tv_series.popular

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

class PopularTvSeriesViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _popularTvSeriesResponse = MutableStateFlow<List<TvItem>>(arrayListOf())
    val popularTvSeriesResponse get() = _popularTvSeriesResponse.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun popularTvSeries(page: Int) {
        viewModelScope.launch {
            repo.topRatedTvSeries(page).onEach {
                when (it) {
                    is UiState.Loading -> {
                        _isLoading.value = true
                    }

                    is UiState.Success -> {
                        _popularTvSeriesResponse.value = it.data
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