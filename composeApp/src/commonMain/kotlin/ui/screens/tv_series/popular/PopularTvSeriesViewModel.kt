package ui.screens.tv_series.popular

import data.model.TvSeriesItem
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.common.GenericPaginatedViewModel
import utils.network.UiState

class PopularTvSeriesViewModel(repo: Repository) : GenericPaginatedViewModel<TvSeriesItem>(repo) {
    override fun fetchPage(page: Int): Flow<UiState<List<TvSeriesItem>>> = repo.popularTvSeries(page)

    fun loadPopularTvSeries() = loadItems()
}