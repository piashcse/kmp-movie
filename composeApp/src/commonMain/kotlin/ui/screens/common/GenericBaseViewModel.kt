package ui.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.network.UiState

/**
 * Generic ViewModel that can handle different types of operations
 * This can be used for both paginated and non-paginated data operations
 */
abstract class GenericBaseViewModel<T>(
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GenericUiState<T>())
    val uiState: StateFlow<GenericUiState<T>> = _uiState.asStateFlow()

    protected fun updateLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    protected fun updateData(data: List<T>?) {
        _uiState.update { it.copy(items = data, errorMessage = null) }
    }

    protected fun updateError(error: String?) {
        _uiState.update { it.copy(errorMessage = error, isLoading = false) }
    }

    protected fun updateItems(additionalItems: List<T>) {
        _uiState.update { current ->
            val currentItems = current.items ?: emptyList()
            current.copy(items = currentItems + additionalItems)
        }
    }

    protected fun executeApiCall(
        apiCall: suspend () -> UiState<List<T>>,
        onSuccess: ((List<T>) -> Unit)? = null
    ) {
        viewModelScope.launch {
            updateLoading(true)
            when (val result = apiCall()) {
                is UiState.Success -> {
                    onSuccess?.invoke(result.data)
                    updateData(result.data)
                }
                is UiState.Error -> updateError(result.exception.message)
                is UiState.Loading -> {} // Already handled by updateLoading
            }
            updateLoading(false)
        }
    }
}