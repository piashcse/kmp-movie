package data.remote

import data.model.BaseModelMovie
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovies
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit

interface ApiInterface {
    suspend fun nowPlayingMovies(
        page: Int
    ): BaseModelMovie<MovieItem>

    suspend fun popularMovies(
        page: Int
    ): BaseModelMovie<MovieItem>

    suspend fun topRatedMovies(
        page: Int
    ): BaseModelMovie<MovieItem>

    suspend fun upcomingMovies(
        page: Int
    ): BaseModelMovie<MovieItem>

    suspend fun movieDetail(
        movieId: Int
    ): MovieDetail

    suspend fun movieSearch(
        searchKey: String
    ): BaseModelMovie<MovieItem>

    suspend fun recommendedMovies(
        movieId: Int
    ): BaseModelMovie<MovieItem>
    suspend fun movieCredit(
        movieId: Int
    ): Artist
    suspend fun artistDetail(
        personId: Int
    ): ArtistDetail

    suspend fun airingTodayTvSeries(
        page: Int
    ): BaseModelMovie<TvSeriesItem>

    suspend fun onTheAirTvSeries(
        page: Int
    ): BaseModelMovie<TvSeriesItem>

    suspend fun popularTvSeries(
        page: Int
    ): BaseModelMovie<TvSeriesItem>

    suspend fun topRatedTvSeries(
        page: Int
    ): BaseModelMovie<TvSeriesItem>

    suspend fun tvSeriesDetail(
        seriesId: Int
    ): TvSeriesDetail

    suspend fun recommendedTvSeries(
        seriesId: Int
    ): BaseModelMovie<TvSeriesItem>

    suspend fun creditTvSeries(
        seriesId: Int
    ): Credit

    suspend fun tvSeriesSearch(
        searchKey: String
    ): BaseModelMovie<TvSeriesItem>

    suspend fun artistMoviesAndTvSeries(
        personId: Int
    ): ArtistMovies

}