package ui.artistdetail

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
        MutableStateFlow<UiState<ArtistDetail>>(UiState.Loading)
    val nowPlayingResponse get() = _artistDetailResponse.asStateFlow()

    fun artistDetail(personId: Int) {
        viewModelScope.launch {
            repo.artistDetail(personId).onEach {
                _artistDetailResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}