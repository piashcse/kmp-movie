package data.remote

import com.skydoves.sandwich.ApiResponse
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
import io.ktor.http.URLBuilder
import io.ktor.http.encodedPath

class ApiService : ApiInterface {

    private suspend inline fun <reified T> getPaginated(path: String, page: Int) =
        ApiResponse.suspendOf {
            apiClient.get {
                url {
                    encodedPath = path
                    parameters.append("page", page.toString())
                }
            }.body<BaseModel<T>>()
        }

    private suspend inline fun <reified T> getItem(path: String) =
        ApiResponse.suspendOf {
            apiClient.get {
                url { encodedPath = path }
            }.body<T>()
        }

    private suspend inline fun <reified T> search(path: String, query: String) =
        ApiResponse.suspendOf {
            apiClient.get {
                url {
                    encodedPath = path
                    parameters.append("query", query)
                }
            }.body<BaseModel<T>>()
        }

    // Movie endpoints
    override suspend fun nowPlayingMovies(page: Int) = getPaginated<MovieItem>("movie/now_playing", page)
    override suspend fun popularMovies(page: Int) = getPaginated<MovieItem>("movie/popular", page)
    override suspend fun topRatedMovies(page: Int) = getPaginated<MovieItem>("movie/top_rated", page)
    override suspend fun upcomingMovies(page: Int) = getPaginated<MovieItem>("movie/upcoming", page)
    override suspend fun movieDetail(movieId: Int) = getItem<MovieDetail>("movie/$movieId")
    override suspend fun movieSearch(searchKey: String) = search<MovieItem>("search/movie", searchKey)
    override suspend fun recommendedMovies(movieId: Int) = getPaginated<MovieItem>("movie/$movieId/recommendations", 1)
    override suspend fun movieCredit(movieId: Int) = getItem<Artist>("movie/$movieId/credits")

    // TV Series endpoints
    override suspend fun airingTodayTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/airing_today", page)
    override suspend fun onTheAirTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/on_the_air", page)
    override suspend fun popularTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/popular", page)
    override suspend fun topRatedTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/top_rated", page)
    override suspend fun tvSeriesDetail(seriesId: Int) = getItem<TvSeriesDetail>("tv/$seriesId")
    override suspend fun recommendedTvSeries(seriesId: Int) = getPaginated<TvSeriesItem>("tv/$seriesId/recommendations", 1)
    override suspend fun creditTvSeries(seriesId: Int) = getItem<Credit>("tv/$seriesId/credits")
    override suspend fun tvSeriesSearch(searchKey: String) = search<TvSeriesItem>("search/tv", searchKey)

    // Celebrity endpoints
    override suspend fun popularCelebrity(page: Int) = getPaginated<Celebrity>("person/popular", page)
    override suspend fun trendingCelebrity(page: Int) = getPaginated<Celebrity>("trending/person/week", page)
    override suspend fun celebritySearch(searchKey: String) = search<Celebrity>("search/person", searchKey)
    override suspend fun artistDetail(personId: Int) = getItem<ArtistDetail>("person/$personId")
    override suspend fun artistMoviesAndTvSeries(personId: Int) = getItem<ArtistMovies>("person/$personId/combined_credits")
}