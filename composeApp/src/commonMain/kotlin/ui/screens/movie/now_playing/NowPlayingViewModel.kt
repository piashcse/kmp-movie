package ui.screens.movie.now_playing

import data.model.MovieItem
import data.model.movie_detail.Genre
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.base.GenreFilterableViewModel
import ui.screens.movie.MovieUiState
import utils.network.UiState

class NowPlayingViewModel(private val repo: Repository) : GenreFilterableViewModel<MovieItem, Genre, MovieUiState>(
    initialState = MovieUiState(),
    updateItems = { state, items -> state.copy(movieList = items) },
    getItems = { it.movieList }
) {
    override fun fetchPage(page: Int): Flow<UiState<List<MovieItem>>> = repo.nowPlayingMovie(page)
    override fun fetchPageByGenre(genreId: Int, page: Int): Flow<UiState<List<MovieItem>>> = repo.getMoviesByGenre(genreId, page)
    override fun loadGenres(): Flow<UiState<List<Genre>>> = repo.getMovieGenres()
    override fun updateLoading(state: MovieUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: MovieUiState, error: String?) = state.copy(errorMessage = error)
    override fun updateItemsWithNewData(state: MovieUiState, items: List<MovieItem>) = state.copy(movieList = items)

    init {
        initialize()
    }

    fun loadNowPlayingMovies() {
        loadItems()
    }
}