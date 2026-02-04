package ui.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.component.GenreItem
import utils.Paginator
import utils.network.UiState

/**
 * Base ViewModel for paginated list screens with genre filtering support
 * Extends PaginatedViewModel to add genre filtering capabilities
 *
 * @param T The type of items in the list
 * @param G The type of genre item
 * @param S The type of UI state
 * @param initialState Initial state for the ViewModel
 * @param updateItems Function to update items in the state
 * @param getItems Function to get items from the state
 */
abstract class GenreFilterableViewModel<T, G : GenreItem, S>(
    initialState: S,
    private val updateItems: (S, List<T>) -> S,
    private val getItems: (S) -> List<T>?
) : ViewModel() {

    protected val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    protected val _availableGenres = MutableStateFlow<List<G>>(emptyList())
    val availableGenres: StateFlow<List<G>> = _availableGenres.asStateFlow()

    private val _selectedGenreId = MutableStateFlow<Int?>(null)
    val selectedGenreId: StateFlow<Int?> = _selectedGenreId.asStateFlow()

    private var paginator: Paginator<T>? = null

    /**
     * Load available genres from the repository
     */
    protected abstract fun loadGenres(): Flow<UiState<List<G>>>

    /**
     * Fetch a page of items filtered by genre
     */
    protected abstract fun fetchPageByGenre(genreId: Int, page: Int): Flow<UiState<List<T>>>

    /**
     * Fetch a page of items without genre filtering
     */
    protected abstract fun fetchPage(page: Int): Flow<UiState<List<T>>>

    /**
     * Update the loading state
     */
    protected abstract fun updateLoading(state: S, isLoading: Boolean): S

    /**
     * Update the error state
     */
    protected abstract fun updateError(state: S, error: String?): S

    /**
     * Update items in the state
     */
    protected abstract fun updateItemsWithNewData(state: S, items: List<T>): S

    /**
     * Initialize the ViewModel and load genres
     */
    fun initialize() {
        loadAvailableGenres()
    }

    /**
     * Load available genres
     */
    private fun loadAvailableGenres() {
        viewModelScope.launch {
            loadGenres().collect { result ->
                when (result) {
                    is UiState.Success -> {
                        _availableGenres.value = result.data
                        // Now that genres are loaded, we can initialize the paginator
                        initializePaginator()
                        // Load initial content (with no genre filter)
                        loadItems()
                    }
                    is UiState.Error -> {
                        // Handle error if needed
                        // Still load items even if genres fail to load
                        initializePaginator()
                        loadItems()
                    }
                    is UiState.Loading -> {
                        // Handle loading if needed
                    }
                }
            }
        }
    }

    /**
     * Initialize the paginator after genres are loaded
     */
    private fun initializePaginator() {
        paginator = Paginator(
            scope = viewModelScope,
            initialKey = 1,
            incrementBy = 1,
            onLoadUpdated = { isLoading ->
                _uiState.update { updateLoading(it, isLoading) }
            },
            onRequest = { page -> fetchPageWithCurrentGenre(page) },
            onError = { throwable ->
                _uiState.update { updateError(it, throwable.message) }
            },
            onSuccess = { items, _ ->
                _uiState.update { current ->
                    updateItemsWithNewData(current, getItems(current).orEmpty() + items)
                }
            }
        )
    }

    /**
     * Fetch page based on current genre selection
     */
    private fun fetchPageWithCurrentGenre(page: Int): Flow<UiState<List<T>>> {
        val currentGenreId = _selectedGenreId.value
        return if (currentGenreId != null) {
            fetchPageByGenre(currentGenreId, page)
        } else {
            fetchPage(page)
        }
    }

    /**
     * Select a genre for filtering
     */
    fun selectGenre(genreId: Int?) {
        _selectedGenreId.value = genreId
        refreshItems()
    }

    /**
     * Refresh items based on current genre selection
     */
    private fun refreshItems() {
        // Clear previous items and reset pagination
        _uiState.update { updateItemsWithNewData(it, emptyList()) }
        paginator?.reset()
        loadItems()
    }

    /**
     * Load items based on current genre selection
     */
    fun loadItems() {
        paginator?.loadNextItems()
    }
}