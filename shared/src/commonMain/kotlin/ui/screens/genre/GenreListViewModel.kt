package ui.screens.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.movie_detail.Genre
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import utils.network.UiState

class GenreListViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<Genre>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Genre>>> = _state

    fun loadGenres() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                repository.getMovieGenres().collect { uiState ->
                    _state.value = uiState
                }
            } catch (e: Exception) {
                _state.value = UiState.Error(e)
            }
        }
    }
}