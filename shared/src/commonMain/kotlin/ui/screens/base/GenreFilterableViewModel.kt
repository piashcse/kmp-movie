package ui.screens.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.component.GenreItem
import utils.network.UiState

abstract class GenreFilterableViewModel<T, G : GenreItem, S>(
    initialState: S,
    updateItems: (S, List<T>) -> S,
    getItems: (S) -> List<T>?
) : PaginatedViewModel<T, S>(initialState, updateItems, getItems) {

    protected val _availableGenres = MutableStateFlow<List<G>>(emptyList())
    val availableGenres: StateFlow<List<G>> = _availableGenres.asStateFlow()

    private val _selectedGenreId = MutableStateFlow<Int?>(null)
    val selectedGenreId: StateFlow<Int?> = _selectedGenreId.asStateFlow()

    protected abstract fun loadGenres(): Flow<UiState<List<G>>>

    protected abstract fun fetchPageByGenre(genreId: Int, page: Int): Flow<UiState<List<T>>>

    fun initialize() {
        viewModelScope.launch {
            loadGenres().collect { result ->
                when (result) {
                    is UiState.Success -> _availableGenres.value = result.data
                    is UiState.Error -> {}
                    is UiState.Loading -> {}
                }
                loadItems()
            }
        }
    }

    final override fun fetchPage(page: Int): Flow<UiState<List<T>>> {
        val currentGenreId = _selectedGenreId.value
        return if (currentGenreId != null) {
            fetchPageByGenre(currentGenreId, page)
        } else {
            fetchPageUnfiltered(page)
        }
    }

    protected abstract fun fetchPageUnfiltered(page: Int): Flow<UiState<List<T>>>

    fun selectGenre(genreId: Int?) {
        _selectedGenreId.value = genreId
        resetPagination()
        loadItems()
    }
}
