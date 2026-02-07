package data.remote

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
    ): BaseModel<MovieItem>

    suspend fun popularMovies(
        page: Int
    ): BaseModel<MovieItem>

    suspend fun topRatedMovies(
        page: Int
    ): BaseModel<MovieItem>

    suspend fun upcomingMovies(
        page: Int
    ): BaseModel<MovieItem>

    suspend fun movieDetail(
        movieId: Int
    ): MovieDetail

    suspend fun movieSearch(
        searchKey: String
    ): BaseModel<MovieItem>

    suspend fun recommendedMovies(
        movieId: Int
    ): BaseModel<MovieItem>

    suspend fun movieCredit(
        movieId: Int
    ): Artist

    suspend fun artistDetail(
        personId: Int
    ): ArtistDetail

    suspend fun airingTodayTvSeries(
        page: Int
    ): BaseModel<TvSeriesItem>

    suspend fun onTheAirTvSeries(
        page: Int
    ): BaseModel<TvSeriesItem>

    suspend fun popularTvSeries(
        page: Int
    ): BaseModel<TvSeriesItem>

    suspend fun topRatedTvSeries(
        page: Int
    ): BaseModel<TvSeriesItem>

    suspend fun tvSeriesDetail(
        seriesId: Int
    ): TvSeriesDetail

    suspend fun recommendedTvSeries(
        seriesId: Int
    ): BaseModel<TvSeriesItem>

    suspend fun creditTvSeries(
        seriesId: Int
    ): Credit

    suspend fun tvSeriesSearch(
        searchKey: String
    ): BaseModel<TvSeriesItem>

    suspend fun celebritySearch(
        searchKey: String
    ): BaseModel<Celebrity>

    suspend fun artistMoviesAndTvSeries(
        personId: Int
    ): ArtistMovies

    suspend fun popularCelebrity(
        page: Int
    ): BaseModel<Celebrity>

    suspend fun trendingCelebrity(
        page: Int
    ): BaseModel<Celebrity>

    // Genre endpoints
    suspend fun movieGenres(): List<data.model.movie_detail.Genre>
    suspend fun tvGenres(): List<data.model.tv_detail.Genre>
    suspend fun moviesByGenre(
        genreId: Int,
        page: Int
    ): BaseModel<MovieItem>

    suspend fun tvSeriesByGenre(
        genreId: Int,
        page: Int
    ): BaseModel<TvSeriesItem>

}