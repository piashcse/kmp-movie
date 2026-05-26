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

abstract class PaginatedViewModel<T, S>(
    initialState: S,
    private val updateItems: (S, List<T>) -> S,
    private val getItems: (S) -> List<T>?
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    protected val paginator = Paginator<T>(
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

    protected abstract fun fetchPage(page: Int): Flow<UiState<List<T>>>

    protected abstract fun updateLoading(state: S, isLoading: Boolean): S

    protected abstract fun updateError(state: S, error: String?): S

    fun loadItems() = paginator.loadNextItems()

    protected fun resetPagination() {
        paginator.reset()
        _uiState.update { updateItems(it, emptyList()) }
    }
}
