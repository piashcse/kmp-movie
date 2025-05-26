package ui.movie.detail

import data.model.MovieItem
import data.model.artist.Artist
import data.model.movie_detail.MovieDetail

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val recommendedMovies: List<MovieItem> = emptyList(),
    val movieCredit: Artist? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)