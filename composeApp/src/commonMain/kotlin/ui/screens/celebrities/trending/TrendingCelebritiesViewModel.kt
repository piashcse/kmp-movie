package ui.screens.celebrities.trending

import data.model.celebrities.Celebrity
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class TrendingCelebritiesViewModel(repo: Repository) : GenericPaginatedViewModel<Celebrity>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<Celebrity>>> = repo.trendingCelebrities(page)

    fun loadTrendingCelebrities() = loadItems()
}