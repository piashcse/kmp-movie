package data.remote.upcoming

import data.model.BaseModel
import data.remote.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import utils.AppConstant

class UpcomingMovieApiImpl : UpcomingApiInterface {
    private fun HttpRequestBuilder.upcomingMovie(
        page: Int,
        api_key: String = AppConstant.API_KEY
    ) {
        url {
            takeFrom(AppConstant.BASE_URL)
            encodedPath = "3/movie/upcoming"
            parameters.append("page", page.toString())
            parameters.append("api_key", api_key)
        }
    }

    override suspend fun upcomingMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            upcomingMovie(page)
        }.body()
    }
}