package data.remote

import data.model.BaseModel
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovies
import data.model.celebrities.Celebrity
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath

class ApiService : ApiInterface {
    override suspend fun nowPlayingMovies(
        page: Int,
    ): BaseModel<MovieItem> {
        return client.get {
            url {
                encodedPath = "movie/now_playing"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun popularMovies(
        page: Int,
    ): BaseModel<MovieItem> {
        return client.get {
            url {
                encodedPath = "movie/popular"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun topRatedMovies(
        page: Int,
    ): BaseModel<MovieItem> {
        return client.get {
            url {
                encodedPath = "movie/top_rated"
                parameters.append("page", page.toString())
            }
        }.body()
    }


    override suspend fun upcomingMovies(
        page: Int,
    ): BaseModel<MovieItem> {
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

    override suspend fun movieSearch(searchKey: String): BaseModel<MovieItem> {
        return client.get {
            url {
                encodedPath = "search/movie"
                parameters.append("query", searchKey)
            }
        }.body()
    }

    override suspend fun recommendedMovies(movieId: Int): BaseModel<MovieItem> {
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

    override suspend fun airingTodayTvSeries(page: Int): BaseModel<TvSeriesItem> {
        return client.get {
            url {
                encodedPath = "tv/airing_today"
                parameters.append("page", page.toString())
            }
        }.body()
    }

    override suspend fun onTheAirTvSeries(page: Int): BaseModel<TvSeriesItem> {
        return client.get {
            url {
                encodedPath = "tv/on_the_air"
                parameters.append("page", page.toString())
            }
        }.body()
    }

    override suspend fun popularTvSeries(page: Int): BaseModel<TvSeriesItem> {
        return client.get {
            url {
                encodedPath = "tv/popular"
                parameters.append("page", page.toString())
            }
        }.body()
    }

    override suspend fun topRatedTvSeries(page: Int): BaseModel<TvSeriesItem> {
        return client.get {
            url {
                encodedPath = "tv/top_rated"
                parameters.append("page", page.toString())
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

    override suspend fun recommendedTvSeries(seriesId: Int): BaseModel<TvSeriesItem> {
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

    override suspend fun tvSeriesSearch(searchKey: String): BaseModel<TvSeriesItem> {
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

    override suspend fun popularCelebrity(page: Int): BaseModel<Celebrity> {
        return client.get {
            url {
                encodedPath = "person/popular"
                parameters.append("page", page.toString())
            }
        }.body()
    }

    override suspend fun trendingCelebrity(page: Int): BaseModel<Celebrity> {
        return client.get {
            url {
                encodedPath = "trending/person/week"
                parameters.append("page", page.toString())
            }
        }.body()
    }
}