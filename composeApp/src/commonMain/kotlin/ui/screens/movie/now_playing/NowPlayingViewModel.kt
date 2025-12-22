package ui.screens.movie.now_playing

import data.model.MovieItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class NowPlayingViewModel(repo: Repository) : GenericPaginatedViewModel<MovieItem>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<MovieItem>>> = repo.nowPlayingMovie(page)

    fun loadNowPlayingMovies() = loadItems()
}