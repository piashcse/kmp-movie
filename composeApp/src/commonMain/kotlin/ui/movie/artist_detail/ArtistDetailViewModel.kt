package ui.movie.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.artist.ArtistDetail
import data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import utils.network.UiState

class ArtistDetailViewModel : ViewModel() {
    private val repo = MovieRepository()
    private val _artistDetailResponse =
        MutableStateFlow<ArtistDetail?>(null)
    val artistDetailResponse get() = _artistDetailResponse.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun artistDetail(personId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                repo.artistDetail(personId).onEach {
                    when (it) {
                        is UiState.Loading -> {
                            _isLoading.value = true
                        }

                        is UiState.Success -> {
                            _artistDetailResponse.value = it.data
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