package data.remote.moviedetail

import data.model.moviedetail.MovieDetail

interface MovieDetailApiInterface {
    suspend fun movieDetail(
        movieId: Int
    ): MovieDetail
}