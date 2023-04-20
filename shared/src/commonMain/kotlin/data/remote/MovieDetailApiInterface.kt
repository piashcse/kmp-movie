package data.remote

import data.model.moviedetail.MovieDetail

interface MovieDetailApiInterface {
    suspend fun movieDetail(
        movieId: Int
    ): MovieDetail
}