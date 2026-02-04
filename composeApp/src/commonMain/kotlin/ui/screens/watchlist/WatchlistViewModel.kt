package ui.screens.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.local.WatchlistItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import utils.network.UiState

class WatchlistViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<WatchlistItem>>>(UiState.Loading)
    val state: StateFlow<UiState<List<WatchlistItem>>> = _state

    fun loadWatchlist() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val watchlist = repository.getWatchlist()
                _state.value = UiState.Success(watchlist)
            } catch (e: Exception) {
                _state.value = UiState.Error(e)
            }
        }
    }
}