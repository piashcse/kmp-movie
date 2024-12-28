package ui.tv_series.airing_today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import utils.network.UiState

class AiringTodayTvSeriesViewModel : ViewModel() {
    private val repo = Repository()
    private val _airingTodayTvSeriesResponse = MutableStateFlow<List<TvSeriesItem>>(arrayListOf())
    val airingTodayTvSeriesResponse get() = _airingTodayTvSeriesResponse.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun airingTodayTvSeries(page: Int) {
        viewModelScope.launch {
            repo.airingTodayTvSeries(page).onEach {
                when (it) {
                    is UiState.Loading -> {
                        _isLoading.value = true
                    }

                    is UiState.Success -> {
                        _airingTodayTvSeriesResponse.value = it.data
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