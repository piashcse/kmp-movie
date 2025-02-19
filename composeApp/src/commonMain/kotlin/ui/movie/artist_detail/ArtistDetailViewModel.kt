package ui.movie.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.artist.ArtistDetail
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class ArtistDetailViewModel : ViewModel() {

    private val repo = Repository() // Ensure this is a Singleton or injected manually

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> get() = _uiState.asStateFlow()

    fun fetchArtistDetail(personId: Int) {
        viewModelScope.launch {
            repo.artistDetail(personId).collect { result ->
                handleStateUpdate(result) { state, data -> state.copy(artistDetail = data) }
            }
        }
    }

    private fun <T> handleStateUpdate(
        result: UiState<T>,
        stateUpdater: (ArtistDetailUiState, T?) -> ArtistDetailUiState
    ) {
        _uiState.update { currentState ->
            when (result) {
                is UiState.Loading -> currentState.copy(isLoading = true)
                is UiState.Success -> stateUpdater(currentState, result.data).copy(isLoading = false)
                is UiState.Error -> currentState.copy(isLoading = false, errorMessage = result.exception.message)
            }
        }
    }
}

data class ArtistDetailUiState(
    val artistDetail: ArtistDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)