package ui.screens.celebrities.trending

import data.model.celebrities.Celebrity
import data.repository.Repository
import ui.screens.base.PaginatedViewModel
import ui.screens.celebrities.CelebrityUiState

class TrendingCelebritiesViewModel(private val repo: Repository) : PaginatedViewModel<Celebrity, CelebrityUiState>(
    initialState = CelebrityUiState(),
    updateItems = { state, items -> state.copy(celebrityList = items) },
    getItems = { it.celebrityList }
) {
    override fun fetchPage(page: Int) = repo.trendingCelebrities(page)
    override fun updateLoading(state: CelebrityUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: CelebrityUiState, error: String?) = state.copy(errorMessage = error)
}