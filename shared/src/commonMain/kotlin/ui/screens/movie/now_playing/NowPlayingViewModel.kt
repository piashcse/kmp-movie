package ui.screens.movie.now_playing

import data.model.Genre
import data.model.MovieItem
import data.repository.Repository
import ui.screens.base.GenreFilterableViewModel
import ui.screens.movie.MovieUiState

class NowPlayingViewModel(private val repo: Repository) : GenreFilterableViewModel<MovieItem, Genre, MovieUiState>(
    initialState = MovieUiState(),
    updateItems = { state, items -> state.copy(movieList = items) },
    getItems = { it.movieList }
) {
    override fun fetchPageUnfiltered(page: Int) = repo.nowPlayingMovie(page)
    override fun fetchPageByGenre(genreId: Int, page: Int) = repo.getMoviesByGenre(genreId, page)
    override fun loadGenres() = repo.getMovieGenres()
    override fun updateLoading(state: MovieUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: MovieUiState, error: String?) = state.copy(errorMessage = error)

    init { initialize() }
}
