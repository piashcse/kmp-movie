package ui.screens.movie.popular

import data.model.MovieItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class PopularMovieViewModel(repo: Repository) : GenericPaginatedViewModel<MovieItem>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<MovieItem>>> = repo.popularMovie(page)
    fun loadPopularMovies() = loadItems()
}