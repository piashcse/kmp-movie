package ui.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import utils.Paginator
import utils.network.UiState

/**
 * Base ViewModel for paginated list screens
 * Eliminates code duplication across all list ViewModels
 * 
 * @param T The type of items in the list
 * @param S The type of UI state
 * @param initialState Initial state for the ViewModel
 * @param updateItems Function to update items in the state
 * @param getItems Function to get items from the state
 */
abstract class PaginatedViewModel<T, S>(
    initialState: S,
    private val updateItems: (S, List<T>) -> S,
    private val getItems: (S) -> List<T>?
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val paginator = Paginator<T>(
        scope = viewModelScope,
        initialKey = 1,
        incrementBy = 1,
        onLoadUpdated = { isLoading ->
            _uiState.update { updateLoading(it, isLoading) }
        },
        onRequest = { page -> fetchPage(page) },
        onError = { throwable ->
            _uiState.update { updateError(it, throwable.message) }
        },
        onSuccess = { items, _ ->
            _uiState.update { current ->
                updateItems(current, getItems(current).orEmpty() + items)
            }
        }
    )

    /**
     * Fetch a page of items from the repository
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
     * Load the next page of items
     */
    fun loadItems() = paginator.loadNextItems()
}
