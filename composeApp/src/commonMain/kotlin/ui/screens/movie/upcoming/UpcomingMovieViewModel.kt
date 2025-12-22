package ui.screens.movie.upcoming

import data.model.MovieItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class UpcomingMovieViewModel(repo: Repository) : GenericPaginatedViewModel<MovieItem>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<MovieItem>>> = repo.upComingMovie(page)

    fun loadUpcomingMovies() = loadItems()
}