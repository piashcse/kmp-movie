package data.remote

import data.model.BaseModel
import data.model.BaseModelTV
import data.model.BaseModelV2
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit

interface ApiInterface {
    suspend fun nowPlayingMovieList(
        page: Int
    ): BaseModel

    suspend fun popularMovieList(
        page: Int
    ): BaseModelV2

    suspend fun topRatedMovieList(
        page: Int
    ): BaseModelV2

    suspend fun upcomingMovieList(
        page: Int
    ): BaseModel

    suspend fun movieDetail(
        movieId: Int
    ): MovieDetail

    suspend fun movieSearch(
        searchKey: String
    ): BaseModelV2

    suspend fun recommendedMovie(
        movieId: Int
    ): BaseModelV2
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

}