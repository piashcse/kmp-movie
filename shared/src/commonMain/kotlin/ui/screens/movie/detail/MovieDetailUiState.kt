package ui.screens.movie.detail

import androidx.compose.runtime.Immutable
import data.model.MovieItem
import data.model.artist.Artist
import data.model.movie_detail.MovieDetail

@Immutable
data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val recommendedMovies: List<MovieItem> = emptyList(),
    val movieCredit: Artist? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)