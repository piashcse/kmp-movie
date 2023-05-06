package data.remote

import data.model.BaseModel
import data.model.BaseModelV2
import data.model.moviedetail.MovieDetail
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.encodedPath

class ApiImpl : ApiInterface {
    private fun HttpRequestBuilder.nowPlayingMovie(
        page: Int
    ) {
        url {
            encodedPath = "3/movie/now_playing"
            parameters.append("page", page.toString())
        }
    }

    private fun HttpRequestBuilder.popularMovie(
        page: Int
    ) {
        url {
            encodedPath = "3/movie/popular"
            parameters.append("page", page.toString())
        }
    }

    private fun HttpRequestBuilder.topRatedMovie(
        page: Int
    ) {
        url {
            encodedPath = "3/movie/top_rated"
            parameters.append("page", page.toString())
        }
    }

    private fun HttpRequestBuilder.upcomingMovie(
        page: Int,
    ) {
        url {
            encodedPath = "3/movie/upcoming"
            parameters.append("page", page.toString())
        }
    }

    private fun HttpRequestBuilder.movieDetail(
        movieId: Int,
    ) {
        url {
            encodedPath = "3/movie/$movieId"
        }
    }

    private fun HttpRequestBuilder.movieSearch(
        searchKey: String,
    ) {
        url {
            encodedPath = "3/search/movie"
            parameters.append("query", searchKey)
        }
    }

    private fun HttpRequestBuilder.recommendedMovie(
        movieId: Int,
    ) {
        url {
            encodedPath = "3/movie/$movieId/recommendations"
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

    override suspend fun movieSearch(searchKey: String): BaseModelV2 {
        return client.get {
            movieSearch(searchKey)
        }.body()
    }

    override suspend fun recommendedMovie(movieId: Int): BaseModelV2 {
        return client.get {
            recommendedMovie(movieId)
        }.body()
    }

}