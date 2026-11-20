package ui.screens.base

import androidx.compose.runtime.Immutable

@Immutable
data class PagedListUiState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val endReached: Boolean = false,
)
