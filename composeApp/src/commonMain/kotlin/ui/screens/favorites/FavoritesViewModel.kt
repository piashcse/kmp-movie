package ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.local.FavoriteItem
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import utils.network.UiState

class FavoritesViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<FavoriteItem>>>(UiState.Loading)
    val state: StateFlow<UiState<List<FavoriteItem>>> = _state

    fun loadFavorites() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val favorites = repository.getFavorites()
                _state.value = UiState.Success(favorites)
            } catch (e: Exception) {
                _state.value = UiState.Error(e)
            }
        }
    }
}