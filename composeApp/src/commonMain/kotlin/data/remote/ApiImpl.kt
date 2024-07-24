package data.remote

import data.model.BaseModel
import data.model.BaseModelV2
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.moviedetail.MovieDetail
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath

class ApiImpl : ApiInterface {
    override suspend fun nowPlayingMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            url {
                encodedPath = "3/movie/now_playing"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun popularMovieList(
        page: Int,
    ): BaseModelV2 {
        return client.get {
            url {
                encodedPath = "3/movie/popular"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun topRatedMovieList(
        page: Int,
    ): BaseModelV2 {
        return client.get {
            url {
                encodedPath = "3/movie/top_rated"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun upcomingMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            url {
                encodedPath = "3/movie/upcoming"
                parameters.append("page", page.toString())
            }
        }.body()
    }

    override suspend fun movieDetail(movieId: Int): MovieDetail {
        return client.get {
            url {
                encodedPath = "3/movie/$movieId"
            }
        }.body()
    }

    override suspend fun movieSearch(searchKey: String): BaseModelV2 {
        return client.get {
            url {
                encodedPath = "3/search/movie"
                parameters.append("query", searchKey)
            }
        }.body()
    }

    override suspend fun recommendedMovie(movieId: Int): BaseModelV2 {
        return client.get {
            url {
                encodedPath = "3/movie/$movieId/recommendations"
            }
        }.body()
    }

    override suspend fun movieCredit(movieId: Int): Artist {
        return client.get {
            url {
                encodedPath = "3/movie/$movieId/credits"
            }
        }.body()
    }

    override suspend fun artistDetail(personId: Int): ArtistDetail {
        return client.get {
            url {
                encodedPath = "3/person/$personId"
            }
        }.body()
    }

}