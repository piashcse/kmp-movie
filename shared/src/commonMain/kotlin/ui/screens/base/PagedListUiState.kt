package ui.screens.base

import androidx.compose.runtime.Immutable

@Immutable
data class PagedListUiState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
