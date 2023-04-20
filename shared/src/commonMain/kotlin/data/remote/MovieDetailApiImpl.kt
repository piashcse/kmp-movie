package data.remote

import data.model.moviedetail.MovieDetail
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import utils.AppConstant

class MovieDetailApiImpl : MovieDetailApiInterface {
    private fun HttpRequestBuilder.movieDetail(
        movieId: Int,
        api_key: String = AppConstant.API_KEY
    ) {
        url {
            takeFrom(AppConstant.BASE_URL)
            encodedPath = "3/movie/$movieId"
            parameters.append("api_key", api_key)
        }
    }

    override suspend fun movieDetail(movieId: Int): MovieDetail {
        return client.get {
            movieDetail(movieId)
        }.body()
    }
}