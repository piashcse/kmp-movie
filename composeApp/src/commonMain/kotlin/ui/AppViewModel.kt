package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.MovieItem
import data.model.TvItem
import data.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import utils.network.UiState

@ExperimentalCoroutinesApi
class AppViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _movieSearchData = MutableStateFlow<List<MovieItem>>(arrayListOf())
    val movieSearchData get() = _movieSearchData.asStateFlow()
    private val _tvSeriesSearchData = MutableStateFlow<List<TvItem>>(arrayListOf())
    val tvSeriesSearchData get() = _tvSeriesSearchData.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    @FlowPreview
    fun movieSearch(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300).filter {
                it.trim().isEmpty().not()
            }.distinctUntilChanged().flatMapLatest {
                repo.searchMovie(it)
            }.collect {
                when (it) {
                    is UiState.Loading -> {
                        _isLoading.value = true
                    }

                    is UiState.Success -> {
                        _movieSearchData.value = it.data.results
                        _isLoading.value = false
                    }

                    is UiState.Error -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    @FlowPreview
    fun tvSeriesSearch(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300).filter {
                it.trim().isEmpty().not()
            }.distinctUntilChanged().flatMapLatest {
                repo.searchTvSeries(it)
            }.collect {
                when (it) {
                    is UiState.Loading -> {
                        _isLoading.value = true
                    }

                    is UiState.Success -> {
                        _tvSeriesSearchData.value = it.data.results
                        _isLoading.value = false
                    }

                    is UiState.Error -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}