package ui.screens.tv_series.top_rated

import data.model.Genre
import data.model.TvSeriesItem
import data.repository.Repository
import ui.screens.base.GenreFilterableViewModel

class TopRatedTvSeriesViewModel(private val repo: Repository) : GenreFilterableViewModel<TvSeriesItem, Genre>() {
    override fun fetchPageUnfiltered(page: Int) = repo.topRatedTvSeries(page)
    override fun fetchPageByGenre(genreId: Int, page: Int) =
        repo.getTvSeriesByGenre(genreId, page)

    override fun loadGenres() = repo.getTvGenres()

    init { initialize() }
}
