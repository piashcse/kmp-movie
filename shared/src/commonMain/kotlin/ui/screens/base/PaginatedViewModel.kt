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

abstract class PaginatedViewModel<T> : ViewModel() {

    private val _uiState = MutableStateFlow(PagedListUiState<T>())
    val uiState: StateFlow<PagedListUiState<T>> = _uiState.asStateFlow()

    protected val paginator = Paginator<T>(
        scope = viewModelScope,
        onLoadUpdated = { isLoading ->
            _uiState.update {
                it.copy(
                    isLoading = isLoading,
                    errorMessage = if (isLoading) null else it.errorMessage,
                )
            }
        },
        onRequest = { page -> fetchPage(page) },
        onError = { throwable ->
            _uiState.update { it.copy(errorMessage = throwable.message) }
        },
        onSuccess = { items, _ ->
            _uiState.update { it.copy(items = it.items + items) }
        },
        onEndReached = {
            _uiState.update { it.copy(endReached = true) }
        },
    )

    protected abstract fun fetchPage(page: Int): Flow<UiState<List<T>>>

    fun loadItems() = paginator.loadNextItems()

    protected fun resetPagination() {
        paginator.reset()
        _uiState.update { it.copy(items = emptyList(), errorMessage = null, endReached = false) }
    }
}
