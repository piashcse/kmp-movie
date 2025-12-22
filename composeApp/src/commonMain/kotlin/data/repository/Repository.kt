package data.repository

import data.remote.ApiService
import utils.flowWithResults
import utils.flowDirect
import utils.network.UiState

class Repository(private val api: ApiService) {

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
}