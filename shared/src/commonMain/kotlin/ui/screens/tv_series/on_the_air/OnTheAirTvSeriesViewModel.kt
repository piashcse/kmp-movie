package ui.screens.tv_series.on_the_air

import data.model.Genre
import data.model.TvSeriesItem
import data.repository.Repository
import ui.screens.base.GenreFilterableViewModel

class OnTheAirTvSeriesViewModel(private val repo: Repository) : GenreFilterableViewModel<TvSeriesItem, Genre>() {
    override fun fetchPageUnfiltered(page: Int) = repo.onTheAirTvSeries(page)
    override fun fetchPageByGenre(genreId: Int, page: Int) =
        repo.getTvSeriesByGenre(genreId, page)

    override fun loadGenres() = repo.getTvGenres()

    init { initialize() }
}
