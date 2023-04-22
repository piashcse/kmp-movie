package data.remote.pupular

import data.model.BaseModelV2
import data.remote.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import utils.AppConstant

class PopularMovieApiImpl : PopularApiInterface {
    private fun HttpRequestBuilder.popularMovie(
        page: Int,
        api_key: String = AppConstant.API_KEY
    ) {
        url {
            takeFrom(AppConstant.BASE_URL)
            encodedPath = "3/movie/popular"
            parameters.append("page", page.toString())
            parameters.append("api_key", api_key)
        }
    }

    override suspend fun popularMovieList(
        page: Int,
    ): BaseModelV2 {
        return client.get {
            popularMovie(page)
        }.body()
    }
}