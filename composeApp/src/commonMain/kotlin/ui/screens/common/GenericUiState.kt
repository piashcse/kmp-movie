package ui.screens.common

/**
 * Generic UI state that can be used across different screens
 * This replaces the need for individual UI state classes like MovieUiState, TvSeriesUiState, etc.
 */
data class GenericUiState<T>(
    val items: List<T>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)