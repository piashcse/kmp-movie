package data.remote

import com.skydoves.sandwich.ApiResponse
import data.model.BaseModel
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovies
import data.model.celebrities.Celebrity
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit

interface ApiInterface {
    suspend fun nowPlayingMovies(
        page: Int
    ): ApiResponse<BaseModel<MovieItem>>

    suspend fun popularMovies(
        page: Int
    ): ApiResponse<BaseModel<MovieItem>>

    suspend fun topRatedMovies(
        page: Int
    ): ApiResponse<BaseModel<MovieItem>>

    suspend fun upcomingMovies(
        page: Int
    ): ApiResponse<BaseModel<MovieItem>>

    suspend fun movieDetail(
        movieId: Int
    ): ApiResponse<MovieDetail>

    suspend fun movieSearch(
        searchKey: String
    ): ApiResponse<BaseModel<MovieItem>>

    suspend fun recommendedMovies(
        movieId: Int
    ): ApiResponse<BaseModel<MovieItem>>

    suspend fun movieCredit(
        movieId: Int
    ): ApiResponse<Artist>

    suspend fun artistDetail(
        personId: Int
    ): ApiResponse<ArtistDetail>

    suspend fun airingTodayTvSeries(
        page: Int
    ): ApiResponse<BaseModel<TvSeriesItem>>

    suspend fun onTheAirTvSeries(
        page: Int
    ): ApiResponse<BaseModel<TvSeriesItem>>

    suspend fun popularTvSeries(
        page: Int
    ): ApiResponse<BaseModel<TvSeriesItem>>

    suspend fun topRatedTvSeries(
        page: Int
    ): ApiResponse<BaseModel<TvSeriesItem>>

    suspend fun tvSeriesDetail(
        seriesId: Int
    ): ApiResponse<TvSeriesDetail>

    suspend fun recommendedTvSeries(
        seriesId: Int
    ): ApiResponse<BaseModel<TvSeriesItem>>

    suspend fun creditTvSeries(
        seriesId: Int
    ): ApiResponse<Credit>

    suspend fun tvSeriesSearch(
        searchKey: String
    ): ApiResponse<BaseModel<TvSeriesItem>>

    suspend fun celebritySearch(
        searchKey: String
    ): ApiResponse<BaseModel<Celebrity>>

    suspend fun artistMoviesAndTvSeries(
        personId: Int
    ): ApiResponse<ArtistMovies>

    suspend fun popularCelebrity(
        page: Int
    ): ApiResponse<BaseModel<Celebrity>>

    suspend fun trendingCelebrity(
        page: Int
    ): ApiResponse<BaseModel<Celebrity>>

}