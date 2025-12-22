package ui.screens.tv_series.airing_today

import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class AiringTodayTvSeriesViewModel(repo: Repository) : GenericPaginatedViewModel<TvSeriesItem>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<TvSeriesItem>>> = repo.airingTodayTvSeries(page)

    fun loadAiringTodayTvSeries() = loadItems()
}