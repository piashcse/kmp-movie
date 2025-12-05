package ui.screens.tv_series.airing_today

import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.base.PaginatedViewModel
import ui.screens.tv_series.TvSeriesUiState
import utils.network.UiState

class AiringTodayTvSeriesViewModel(private val repo: Repository) : PaginatedViewModel<TvSeriesItem, TvSeriesUiState>(
    initialState = TvSeriesUiState(),
    updateItems = { state, items -> state.copy(tvSeriesList = items) },
    getItems = { it.tvSeriesList }
) {
    override fun fetchPage(page: Int): Flow<UiState<List<TvSeriesItem>>> = repo.airingTodayTvSeries(page)
    override fun updateLoading(state: TvSeriesUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: TvSeriesUiState, error: String?) = state.copy(errorMessage = error)

    fun loadAiringTodayTvSeries() = loadItems()
}