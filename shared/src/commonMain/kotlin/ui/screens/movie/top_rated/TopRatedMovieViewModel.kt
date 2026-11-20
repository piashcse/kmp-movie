package ui.screens.movie.top_rated

import data.model.Genre
import data.model.MovieItem
import data.repository.Repository
import ui.screens.base.GenreFilterableViewModel

class TopRatedMovieViewModel(private val repo: Repository) : GenreFilterableViewModel<MovieItem, Genre>() {
    override fun fetchPageUnfiltered(page: Int) = repo.topRatedMovie(page)
    override fun fetchPageByGenre(genreId: Int, page: Int) =
        repo.getMoviesByGenre(genreId, page)

    override fun loadGenres() = repo.getMovieGenres()

    init { initialize() }
}
