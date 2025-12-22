package ui.screens.celebrities.popular

import data.model.celebrities.Celebrity
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class PopularCelebritiesViewModel(repo: Repository) : GenericPaginatedViewModel<Celebrity>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<Celebrity>>> = repo.popularCelebrities(page)

    fun loadPopularCelebrities() = loadItems()
}