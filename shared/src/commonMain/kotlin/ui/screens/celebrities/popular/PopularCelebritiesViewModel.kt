package ui.screens.celebrities.popular

import data.model.celebrities.Celebrity
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.base.PaginatedViewModel
import ui.screens.celebrities.CelebrityUiState
import utils.network.UiState

class PopularCelebritiesViewModel(private val repo: Repository) : PaginatedViewModel<Celebrity, CelebrityUiState>(
    initialState = CelebrityUiState(),
    updateItems = { state, items -> state.copy(celebrityList = items) },
    getItems = { it.celebrityList }
) {
    override fun fetchPage(page: Int): Flow<UiState<List<Celebrity>>> = repo.popularCelebrities(page)
    override fun updateLoading(state: CelebrityUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: CelebrityUiState, error: String?) = state.copy(errorMessage = error)

    fun loadPopularCelebrities() = loadItems()
}