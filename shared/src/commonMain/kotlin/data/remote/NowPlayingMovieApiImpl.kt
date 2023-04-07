package data.remote

import data.remote.model.BaseModel
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import utils.AppConstant

class NowPlayingMovieApiImpl : ApiInterface {
    private fun HttpRequestBuilder.nowPlayingMovie(
        page: Int,
        api_key: String = AppConstant.API_KEY
    ) {
        url {
            takeFrom(AppConstant.BASE_URL)
            encodedPath = "3/movie/now_playing"
            parameters.append("page", page.toString())
            parameters.append("api_key", api_key)
        }
    }

    override suspend fun nowPlayingMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            nowPlayingMovie(page)
        }.body()
    }
}