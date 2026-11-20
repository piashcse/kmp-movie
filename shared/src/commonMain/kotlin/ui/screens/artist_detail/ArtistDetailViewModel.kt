package ui.screens.artist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

class ArtistDetailViewModel(private val repo: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> get() = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var currentPersonId: Int? = null

    fun fetchArtistDetail(personId: Int) {
        if (currentPersonId == personId && _uiState.value.artistDetail != null) return

        currentPersonId = personId
        fetchJob?.cancel()
        _uiState.value = ArtistDetailUiState()
        fetchJob = viewModelScope.launch {
            repo.artistDetail(personId).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is UiState.Loading -> state.copy(isLoading = true)
                        is UiState.Success -> {
                            val detail = result.data
                            state.copy(
                                artistDetail = detail,
                                artistMovies = detail.combinedCredits?.cast.orEmpty(),
                                isLoading = false,
                            )
                        }

                        is UiState.Error -> state.copy(
                            isLoading = false,
                            errorMessage = result.exception.message,
                        )
                    }
                }
            }
        }
    }
}
