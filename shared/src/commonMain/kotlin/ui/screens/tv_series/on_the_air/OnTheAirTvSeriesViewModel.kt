package ui.screens.tv_series.on_the_air

import data.model.Genre
import data.model.TvSeriesItem
import data.repository.Repository
import ui.screens.base.GenreFilterableViewModel
import ui.screens.tv_series.TvSeriesUiState

class OnTheAirTvSeriesViewModel(private val repo: Repository) : GenreFilterableViewModel<TvSeriesItem, Genre, TvSeriesUiState>(
    initialState = TvSeriesUiState(),
    updateItems = { state, items -> state.copy(tvSeriesList = items) },
    getItems = { it.tvSeriesList }
) {
    override fun fetchPageUnfiltered(page: Int) = repo.onTheAirTvSeries(page)
    override fun fetchPageByGenre(genreId: Int, page: Int) = repo.getTvSeriesByGenre(genreId, page)
    override fun loadGenres() = repo.getTvGenres()
    override fun updateLoading(state: TvSeriesUiState, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: TvSeriesUiState, error: String?) = state.copy(errorMessage = error)

    init { initialize() }
}
