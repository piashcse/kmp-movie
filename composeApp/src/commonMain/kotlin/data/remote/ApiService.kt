package data.remote

import data.model.BaseModel
import data.model.BaseModelTV
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovies
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath

class ApiService : ApiInterface {
    override suspend fun nowPlayingMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            url {
                encodedPath = "movie/now_playing"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun popularMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            url {
                encodedPath = "movie/popular"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun topRatedMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            url {
                encodedPath = "movie/top_rated"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun upcomingMovieList(
        page: Int,
    ): BaseModel {
        return client.get {
            url {
                encodedPath = "movie/upcoming"
                parameters.append("page", page.toString())
            }
        }.body()
    }

    override suspend fun movieDetail(movieId: Int): MovieDetail {
        return client.get {
            url {
                encodedPath = "movie/$movieId"
            }
        }.body()
    }

    override suspend fun movieSearch(searchKey: String): BaseModel {
        return client.get {
            url {
                encodedPath = "search/movie"
                parameters.append("query", searchKey)
            }
        }.body()
    }

    override suspend fun recommendedMovie(movieId: Int): BaseModel {
        return client.get {
            url {
                encodedPath = "movie/$movieId/recommendations"
            }
        }.body()
    }

    override suspend fun movieCredit(movieId: Int): Artist {
        return client.get {
            url {
                encodedPath = "movie/$movieId/credits"
            }
        }.body()
    }

    override suspend fun artistDetail(personId: Int): ArtistDetail {
        return client.get {
            url {
                encodedPath = "person/$personId"
            }
        }.body()
    }

    override suspend fun airingTodayTvSeries(page: Int): BaseModelTV {
        return client.get {
            url {
                encodedPath = "tv/airing_today"
            }
        }.body()
    }

    override suspend fun onTheAirTvSeries(page: Int): BaseModelTV {
        return client.get {
            url {
                encodedPath = "tv/on_the_air"
            }
        }.body()
    }

    override suspend fun popularTvSeries(page: Int): BaseModelTV {
        return client.get {
            url {
                encodedPath = "tv/popular"
            }
        }.body()
    }

    override suspend fun topRatedTvSeries(page: Int): BaseModelTV {
        return client.get {
            url {
                encodedPath = "tv/top_rated"
            }
        }.body()
    }

    override suspend fun tvSeriesDetail(seriesId: Int): TvSeriesDetail {
        return client.get {
            url {
                encodedPath = "tv/${seriesId}"
            }
        }.body()
    }

    override suspend fun recommendedTvSeries(seriesId: Int): BaseModelTV {
        return client.get {
            url {
                encodedPath = "tv/${seriesId}/recommendations"
            }
        }.body()
    }

    override suspend fun creditTvSeries(seriesId: Int): Credit {
        return client.get {
            url {
                encodedPath = "tv/${seriesId}/credits"
            }
        }.body()
    }

    override suspend fun tvSeriesSearch(searchKey: String): BaseModelTV {
        return client.get {
            url {
                encodedPath = "search/tv"
                parameters.append("query", searchKey)
            }
        }.body()
    }

    override suspend fun artistMoviesAndTvSeries(personId: Int): ArtistMovies {
        return client.get {
            url {
                encodedPath = "person/${personId}/combined_credits"
            }
        }.body()
    }
}