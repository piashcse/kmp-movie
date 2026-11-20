package data.remote

import data.model.BaseModel
import data.model.Genre
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.artist.ArtistDetail
import data.model.celebrities.Celebrity
import data.model.genre.GenreResponse
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath

class ApiService {
    
    // Helper function for paginated endpoints with reified
    private suspend inline fun <reified T> getPaginated(
        path: String,
        page: Int,
        noinline additionalParams: ((io.ktor.http.URLBuilder) -> Unit)? = null
    ): BaseModel<T> = apiClient.get {
        url {
            encodedPath = path
            parameters.append("page", page.toString())
            additionalParams?.invoke(this)
        }
    }.body()
    
    // Helper for single item endpoints with optional query parameters.
    private suspend inline fun <reified T> getItem(
        path: String,
        noinline additionalParams: ((io.ktor.http.URLBuilder) -> Unit)? = null,
    ): T = apiClient.get {
        url {
            encodedPath = path
            additionalParams?.invoke(this)
        }
    }.body()
    
    // Helper function for search endpoints
    private suspend inline fun <reified T> search(
        path: String,
        query: String
    ): BaseModel<T> = apiClient.get {
        url {
            encodedPath = path
            parameters.append("query", query)
        }
    }.body()
    
    // Movie endpoints
    suspend fun nowPlayingMovies(page: Int) = getPaginated<MovieItem>("movie/now_playing", page, null)
    suspend fun popularMovies(page: Int) = getPaginated<MovieItem>("movie/popular", page, null)
    suspend fun topRatedMovies(page: Int) = getPaginated<MovieItem>("movie/top_rated", page, null)
    suspend fun upcomingMovies(page: Int) = getPaginated<MovieItem>("movie/upcoming", page, null)
    suspend fun movieDetail(movieId: Int) = getItem<MovieDetail>("movie/$movieId") {
        it.parameters.append("append_to_response", "credits,recommendations")
    }
    suspend fun movieSearch(searchKey: String) = search<MovieItem>("search/movie", searchKey)

    // TV Series endpoints
    suspend fun airingTodayTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/airing_today", page, null)
    suspend fun onTheAirTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/on_the_air", page, null)
    suspend fun popularTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/popular", page, null)
    suspend fun topRatedTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/top_rated", page, null)
    suspend fun tvSeriesDetail(seriesId: Int) = getItem<TvSeriesDetail>("tv/$seriesId") {
        it.parameters.append("append_to_response", "credits,recommendations")
    }
    suspend fun tvSeriesSearch(searchKey: String) = search<TvSeriesItem>("search/tv", searchKey)

    // Celebrity endpoints
    suspend fun popularCelebrity(page: Int) = getPaginated<Celebrity>("person/popular", page, null)
    suspend fun trendingCelebrity(page: Int) = getPaginated<Celebrity>("trending/person/week", page, null)
    suspend fun celebritySearch(searchKey: String) = search<Celebrity>("search/person", searchKey)
    suspend fun artistDetail(personId: Int) = getItem<ArtistDetail>("person/$personId") {
        it.parameters.append("append_to_response", "combined_credits")
    }

    // Genre endpoints
    suspend fun movieGenres(): List<Genre> = apiClient.get { url { encodedPath = "genre/movie/list" } }.body<GenreResponse>().genres
    suspend fun tvGenres(): List<Genre> = apiClient.get { url { encodedPath = "genre/tv/list" } }.body<GenreResponse>().genres
    suspend fun moviesByGenre(genreId: Int, page: Int): BaseModel<MovieItem> = getPaginated<MovieItem>("discover/movie", page) { url -> url.parameters.append("with_genres", genreId.toString()) }
    suspend fun tvSeriesByGenre(genreId: Int, page: Int): BaseModel<TvSeriesItem> = getPaginated<TvSeriesItem>("discover/tv", page) { url -> url.parameters.append("with_genres", genreId.toString()) }
}