package data.remote.toprated

import data.model.BaseModel
import data.model.BaseModelV2
import data.remote.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import utils.AppConstant

class TopRatedMovieApiImpl : TopRatedApiInterface {
    private fun HttpRequestBuilder.topRatedMovie(
        page: Int,
        api_key: String = AppConstant.API_KEY
    ) {
        url {
            takeFrom(AppConstant.BASE_URL)
            encodedPath = "3/movie/top_rated"
            parameters.append("page", page.toString())
            parameters.append("api_key", api_key)
        }
    }

    override suspend fun topRatedMovieList(
        page: Int,
    ): BaseModelV2 {
        return client.get {
            topRatedMovie(page)
        }.body()
    }
}