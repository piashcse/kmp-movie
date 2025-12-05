package ui.screens.tv_series.top_rated

import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.base.PaginatedViewModel
import ui.screens.tv_series.TvSeriesUiState
import utils.network.UiState

class TopRatedTvSeriesViewModel(private val repo: Repository) : PaginatedViewModel<TvSeriesItem, TvSeriesUiState>(
    initialState = TvSeriesUiState(),
    updateItems = { state, items -> state.copy(tvSeriesList = items) },
    getItems = { it.tvSeriesList }
) {
    override fun fetchPage(page: Int): Flow<UiState<List<TvSeriesItem>>> = repo.topRatedTvSeries(page)
    override fun updateLoading(state: TvSeriesUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: TvSeriesUiState, error: String?) = state.copy(errorMessage = error)

    fun loadTopRatedTvSeries() = loadItems()
}