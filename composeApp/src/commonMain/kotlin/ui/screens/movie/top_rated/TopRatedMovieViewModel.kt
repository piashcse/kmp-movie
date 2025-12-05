package ui.screens.movie.top_rated

import data.model.MovieItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.base.PaginatedViewModel
import ui.screens.movie.MovieUiState
import utils.network.UiState

class TopRatedMovieViewModel(private val repo: Repository) : PaginatedViewModel<MovieItem, MovieUiState>(
    initialState = MovieUiState(),
    updateItems = { state, items -> state.copy(movieList = items) },
    getItems = { it.movieList }
) {
    override fun fetchPage(page: Int): Flow<UiState<List<MovieItem>>> = repo.topRatedMovie(page)
    override fun updateLoading(state: MovieUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: MovieUiState, error: String?) = state.copy(errorMessage = error)

    fun loadTopRatedMovies() = loadItems()
}