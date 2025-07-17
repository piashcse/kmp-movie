package ui.screens.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class ArtistDetailViewModel(private val repo: Repository = Repository()) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> get() = _uiState.asStateFlow()

    fun fetchArtistDetail(personId: Int) {
        viewModelScope.launch {
            launch { artistDetail(personId) }
            launch { artistMoviesAndTvShows(personId) }
        }
    }

    private suspend fun artistDetail(personId: Int) {
        repo.artistDetail(personId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(artistDetail = data) }
        }
    }

    private suspend fun artistMoviesAndTvShows(personId: Int) {
        repo.artistMoviesAndTvShows(personId).collect { result ->
            handleStateUpdate(result) { state, data -> state.copy(artistMovies = data?.cast) }
        }
    }

    private fun <T> handleStateUpdate(
        result: UiState<T>, stateUpdater: (ArtistDetailUiState, T?) -> ArtistDetailUiState
    ) {
        _uiState.update { currentState ->
            when (result) {
                is UiState.Loading -> currentState.copy(isLoading = true)
                is UiState.Success -> stateUpdater(
                    currentState, result.data
                ).copy(isLoading = false)

                is UiState.Error -> currentState.copy(
                    isLoading = false, errorMessage = result.exception.message
                )
            }
        }
    }
}