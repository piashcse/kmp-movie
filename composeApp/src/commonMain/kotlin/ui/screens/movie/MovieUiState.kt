package ui.screens.movie

import data.model.MovieItem

data class MovieUiState(
    val movieList: List<MovieItem>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
