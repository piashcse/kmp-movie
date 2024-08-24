package ui.tv_series.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.TvItem
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import utils.network.UiState

class TvSeriesDetailViewModel : ViewModel() {
    private val repo = MovieRepository()

    private val _tvSeriesDetail = MutableStateFlow<TvSeriesDetail?>(null)
    val tvSeriesDetail get() = _tvSeriesDetail.asStateFlow()

    private val _recommendedTvSeries = MutableStateFlow<List<TvItem>>(arrayListOf())
    val recommendedTvSeries get() = _recommendedTvSeries.asStateFlow()

    private val _creditTvSeries = MutableStateFlow<Credit?>(null)
    val creditTvSeries get() = _creditTvSeries.asStateFlow()


    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun tvSeriesDetail(seriesId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                repo.tvSeriesDetail(seriesId).onEach {
                    when (it) {
                        is UiState.Loading -> {
                            _isLoading.value = true
                        }

                        is UiState.Success -> {
                            _tvSeriesDetail.value = it.data
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
    fun recommendedTvSeries(seriesId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                repo.recommendedTvSeries(seriesId).onEach {
                    when (it) {
                        is UiState.Loading -> {
                            _isLoading.value = true
                        }

                        is UiState.Success -> {
                            _recommendedTvSeries.value = it.data
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

    fun creditTvSeries(seriesId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                repo.creditTvSeries(seriesId).onEach {
                    when (it) {
                        is UiState.Loading -> {
                            _isLoading.value = true
                        }

                        is UiState.Success -> {
                            _creditTvSeries.value = it.data
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