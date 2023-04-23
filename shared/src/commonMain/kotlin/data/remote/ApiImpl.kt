package data.remote

import data.model.BaseModel
import data.model.BaseModelV2
import data.model.moviedetail.MovieDetail
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import utils.AppConstant

class ApiImpl : ApiInterface{
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

    override suspend fun nowPlayingMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            nowPlayingMovie(page)
        }.body()
    }


    override suspend fun popularMovieList(
        page: Int,
    ): BaseModelV2 {
        return client.get {
            popularMovie(page)
        }.body()
    }


    override suspend fun topRatedMovieList(
        page: Int,
    ): BaseModelV2 {
        return client.get {
            topRatedMovie(page)
        }.body()
    }


    override suspend fun upcomingMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            upcomingMovie(page)
        }.body()
    }

    override suspend fun movieDetail(movieId: Int): MovieDetail {
        return client.get {
            movieDetail(movieId)
        }.body()
    }

}