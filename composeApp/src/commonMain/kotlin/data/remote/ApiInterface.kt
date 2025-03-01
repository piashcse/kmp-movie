package data.remote

import data.model.BaseModel
import data.model.BaseModelTV
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovies
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit

interface ApiInterface {
    suspend fun nowPlayingMovieList(
        page: Int
    ): BaseModel

    suspend fun popularMovieList(
        page: Int
    ): BaseModel

    suspend fun topRatedMovieList(
        page: Int
    ): BaseModel

    suspend fun upcomingMovieList(
        page: Int
    ): BaseModel

    suspend fun movieDetail(
        movieId: Int
    ): MovieDetail

    suspend fun movieSearch(
        searchKey: String
    ): BaseModel

    suspend fun recommendedMovie(
        movieId: Int
    ): BaseModel
    suspend fun movieCredit(
        movieId: Int
    ): Artist
    suspend fun artistDetail(
        personId: Int
    ): ArtistDetail

    suspend fun airingTodayTvSeries(
        page: Int
    ): BaseModelTV

    suspend fun onTheAirTvSeries(
        page: Int
    ): BaseModelTV

    suspend fun popularTvSeries(
        page: Int
    ): BaseModelTV

    suspend fun topRatedTvSeries(
        page: Int
    ): BaseModelTV

    suspend fun tvSeriesDetail(
        seriesId: Int
    ): TvSeriesDetail

    suspend fun recommendedTvSeries(
        seriesId: Int
    ): BaseModelTV

    suspend fun creditTvSeries(
        seriesId: Int
    ): Credit

    suspend fun tvSeriesSearch(
        searchKey: String
    ): BaseModelTV

    suspend fun artistMoviesAndTvSeries(
        personId: Int
    ): ArtistMovies

}