package data.repository

import data.model.local.FavoriteItem
import data.model.local.MediaType

import data.remote.ApiService
import utils.flowDirect
import utils.flowWithResults
import utils.network.UiState
import utils.storage.LocalStorageManager

class Repository(
    private val api: ApiService,
    private val localStorageManager: LocalStorageManager
) {

    // Movie endpoints
    fun nowPlayingMovie(page: Int) = flowWithResults { api.nowPlayingMovies(page) }
    fun popularMovie(page: Int) = flowWithResults { api.popularMovies(page) }
    fun topRatedMovie(page: Int) = flowWithResults { api.topRatedMovies(page) }
    fun upComingMovie(page: Int) = flowWithResults { api.upcomingMovies(page) }
    fun movieDetail(movieId: Int) = flowDirect { api.movieDetail(movieId) }
    fun searchMovie(searchKey: String) = flowDirect { api.movieSearch(searchKey) }
    fun recommendedMovie(movieId: Int) = flowWithResults { api.recommendedMovies(movieId) }
    fun movieCredit(movieId: Int) = flowDirect { api.movieCredit(movieId) }

    // TV Series endpoints
    fun airingTodayTvSeries(page: Int) = flowWithResults { api.airingTodayTvSeries(page) }
    fun onTheAirTvSeries(page: Int) = flowWithResults { api.onTheAirTvSeries(page) }
    fun popularTvSeries(page: Int) = flowWithResults { api.popularTvSeries(page) }
    fun topRatedTvSeries(page: Int) = flowWithResults { api.topRatedTvSeries(page) }
    fun tvSeriesDetail(seriesId: Int) = flowDirect { api.tvSeriesDetail(seriesId) }
    fun recommendedTvSeries(seriesId: Int) = flowWithResults { api.recommendedTvSeries(seriesId) }
    fun creditTvSeries(seriesId: Int) = flowDirect { api.creditTvSeries(seriesId) }
    fun searchTvSeries(searchKey: String) = flowDirect { api.tvSeriesSearch(searchKey) }

    // Celebrity endpoints
    fun popularCelebrities(page: Int) = flowWithResults { api.popularCelebrity(page) }
    fun trendingCelebrities(page: Int) = flowWithResults { api.trendingCelebrity(page) }
    fun searchCelebrity(searchKey: String) = flowDirect { api.celebritySearch(searchKey) }
    fun artistDetail(personId: Int) = flowDirect { api.artistDetail(personId) }
    fun artistMoviesAndTvShows(personId: Int) = flowDirect { api.artistMoviesAndTvSeries(personId) }

    // Favorites endpoints
    suspend fun getFavorites() = localStorageManager.getFavorites()
    suspend fun addFavorite(favoriteItem: FavoriteItem) = localStorageManager.addFavorite(favoriteItem)
    suspend fun removeFavorite(id: Int, mediaType: MediaType) = localStorageManager.removeFavorite(id, mediaType)
    suspend fun isFavorite(id: Int, mediaType: MediaType) = localStorageManager.isFavorite(id, mediaType)



    // Genre endpoints
    fun getMovieGenres() = flowDirect<List<data.model.movie_detail.Genre>> { api.movieGenres() }
    fun getTvGenres() = flowDirect<List<data.model.tv_detail.Genre>> { api.tvGenres() }
    fun getMoviesByGenre(genreId: Int, page: Int) = flowWithResults<data.model.MovieItem> { api.moviesByGenre(genreId, page) }
    fun getTvSeriesByGenre(genreId: Int, page: Int) = flowWithResults<data.model.TvSeriesItem> { api.tvSeriesByGenre(genreId, page) }
}