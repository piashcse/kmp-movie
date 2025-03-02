package data.remote

import data.model.BaseModelMovie
import data.model.BaseModelTVSeries
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovies
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit

interface ApiInterface {
    suspend fun nowPlayingMovieList(
        page: Int
    ): BaseModelMovie

    suspend fun popularMovieList(
        page: Int
    ): BaseModelMovie

    suspend fun topRatedMovieList(
        page: Int
    ): BaseModelMovie

    suspend fun upcomingMovieList(
        page: Int
    ): BaseModelMovie

    suspend fun movieDetail(
        movieId: Int
    ): MovieDetail

    suspend fun movieSearch(
        searchKey: String
    ): BaseModelMovie

    suspend fun recommendedMovie(
        movieId: Int
    ): BaseModelMovie
    suspend fun movieCredit(
        movieId: Int
    ): Artist
    suspend fun artistDetail(
        personId: Int
    ): ArtistDetail

    suspend fun airingTodayTvSeries(
        page: Int
    ): BaseModelTVSeries

    suspend fun onTheAirTvSeries(
        page: Int
    ): BaseModelTVSeries

    suspend fun popularTvSeries(
        page: Int
    ): BaseModelTVSeries

    suspend fun topRatedTvSeries(
        page: Int
    ): BaseModelTVSeries

    suspend fun tvSeriesDetail(
        seriesId: Int
    ): TvSeriesDetail

    suspend fun recommendedTvSeries(
        seriesId: Int
    ): BaseModelTVSeries

    suspend fun creditTvSeries(
        seriesId: Int
    ): Credit

    suspend fun tvSeriesSearch(
        searchKey: String
    ): BaseModelTVSeries

    suspend fun artistMoviesAndTvSeries(
        personId: Int
    ): ArtistMovies

}