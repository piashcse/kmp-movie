package ui.screens.tv_series.airing_today

import data.model.TvSeriesItem
import data.model.tv_detail.Genre
import data.repository.Repository
import kotlinx.coroutines.flow.Flow
import ui.screens.base.GenreFilterableViewModel
import ui.screens.tv_series.TvSeriesUiState
import utils.network.UiState

class AiringTodayTvSeriesViewModel(private val repo: Repository) : GenreFilterableViewModel<TvSeriesItem, Genre, TvSeriesUiState>(
    initialState = TvSeriesUiState(),
    updateItems = { state, items -> state.copy(tvSeriesList = items) },
    getItems = { it.tvSeriesList }
) {
    override fun fetchPage(page: Int): Flow<UiState<List<TvSeriesItem>>> = repo.airingTodayTvSeries(page)
    override fun fetchPageByGenre(genreId: Int, page: Int): Flow<UiState<List<TvSeriesItem>>> = repo.getTvSeriesByGenre(genreId, page)
    override fun loadGenres(): Flow<UiState<List<Genre>>> = repo.getTvGenres()
    override fun updateLoading(state: TvSeriesUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: TvSeriesUiState, error: String?) = state.copy(errorMessage = error)
    override fun updateItemsWithNewData(state: TvSeriesUiState, items: List<TvSeriesItem>) = state.copy(tvSeriesList = items)

    init {
        initialize()
    }

    fun loadAiringTodayTvSeries() {
        loadItems()
    }
}