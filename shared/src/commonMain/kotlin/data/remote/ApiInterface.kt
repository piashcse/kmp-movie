package data.remote

import data.model.BaseModel
import data.model.BaseModelV2
import data.model.moviedetail.MovieDetail

interface ApiInterface{
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
}