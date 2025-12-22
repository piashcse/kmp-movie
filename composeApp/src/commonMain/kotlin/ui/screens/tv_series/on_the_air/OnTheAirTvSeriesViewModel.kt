package ui.screens.tv_series.on_the_air

import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class OnTheAirTvSeriesViewModel(repo: Repository) : GenericPaginatedViewModel<TvSeriesItem>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<TvSeriesItem>>> = repo.onTheAirTvSeries(page)

    fun loadOnTheAirTvSeries() = loadItems()
}