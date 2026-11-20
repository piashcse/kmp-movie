package ui.screens.movie

import androidx.compose.runtime.Immutable
import data.model.MovieItem

@Immutable
data class MovieUiState(
    val movieList: List<MovieItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
