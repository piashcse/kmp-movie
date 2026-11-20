package ui.screens.movie.now_playing

import data.model.Genre
import data.model.MovieItem
import data.repository.Repository
import ui.screens.base.GenreFilterableViewModel

class NowPlayingViewModel(private val repo: Repository) : GenreFilterableViewModel<MovieItem, Genre>() {
    override fun fetchPageUnfiltered(page: Int) = repo.nowPlayingMovie(page)
    override fun fetchPageByGenre(genreId: Int, page: Int) =
        repo.getMoviesByGenre(genreId, page)

    override fun loadGenres() = repo.getMovieGenres()

    init { initialize() }
}
