package ui.screens.tv_series.top_rated

import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class TopRatedTvSeriesViewModel(repo: Repository) : GenericPaginatedViewModel<TvSeriesItem>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<TvSeriesItem>>> = repo.topRatedTvSeries(page)

    fun loadTopRatedTvSeries() = loadItems()
}