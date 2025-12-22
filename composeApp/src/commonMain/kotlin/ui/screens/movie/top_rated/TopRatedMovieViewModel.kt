package ui.screens.movie.top_rated

import data.model.MovieItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class TopRatedMovieViewModel(repo: Repository) : GenericPaginatedViewModel<MovieItem>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<MovieItem>>> = repo.topRatedMovie(page)

    fun loadTopRatedMovies() = loadItems()
}