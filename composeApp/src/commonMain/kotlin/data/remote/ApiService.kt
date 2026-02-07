package data.remote

import data.model.BaseModel
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.artist.Artist
import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovies
import data.model.celebrities.Celebrity
import data.model.genre.GenreResponse
import data.model.movie_detail.MovieDetail
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath

class ApiService : ApiInterface {
    
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
    
    // Helper function for single item endpoints
    private suspend inline fun <reified T> getItem(
        path: String
    ): T = apiClient.get {
        url { encodedPath = path }
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
    override suspend fun nowPlayingMovies(page: Int) = getPaginated<MovieItem>("movie/now_playing", page, null)
    override suspend fun popularMovies(page: Int) = getPaginated<MovieItem>("movie/popular", page, null)
    override suspend fun topRatedMovies(page: Int) = getPaginated<MovieItem>("movie/top_rated", page, null)
    override suspend fun upcomingMovies(page: Int) = getPaginated<MovieItem>("movie/upcoming", page, null)
    override suspend fun movieDetail(movieId: Int) = getItem<MovieDetail>("movie/$movieId")
    override suspend fun movieSearch(searchKey: String) = search<MovieItem>("search/movie", searchKey)
    override suspend fun recommendedMovies(movieId: Int) = getPaginated<MovieItem>("movie/$movieId/recommendations", 1, null)
    override suspend fun movieCredit(movieId: Int) = getItem<Artist>("movie/$movieId/credits")

    // TV Series endpoints
    override suspend fun airingTodayTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/airing_today", page, null)
    override suspend fun onTheAirTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/on_the_air", page, null)
    override suspend fun popularTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/popular", page, null)
    override suspend fun topRatedTvSeries(page: Int) = getPaginated<TvSeriesItem>("tv/top_rated", page, null)
    override suspend fun tvSeriesDetail(seriesId: Int) = getItem<TvSeriesDetail>("tv/$seriesId")
    override suspend fun recommendedTvSeries(seriesId: Int) = getPaginated<TvSeriesItem>("tv/$seriesId/recommendations", 1, null)
    override suspend fun creditTvSeries(seriesId: Int) = getItem<Credit>("tv/$seriesId/credits")
    override suspend fun tvSeriesSearch(searchKey: String) = search<TvSeriesItem>("search/tv", searchKey)

    // Celebrity endpoints
    override suspend fun popularCelebrity(page: Int) = getPaginated<Celebrity>("person/popular", page, null)
    override suspend fun trendingCelebrity(page: Int) = getPaginated<Celebrity>("trending/person/week", page, null)
    override suspend fun celebritySearch(searchKey: String) = search<Celebrity>("search/person", searchKey)
    override suspend fun artistDetail(personId: Int) = getItem<ArtistDetail>("person/$personId")
    override suspend fun artistMoviesAndTvSeries(personId: Int) = getItem<ArtistMovies>("person/$personId/combined_credits")

    // Genre endpoints
    override suspend fun movieGenres(): List<data.model.movie_detail.Genre> = apiClient.get { url { encodedPath = "genre/movie/list" } }.body<GenreResponse>().genres
    override suspend fun tvGenres(): List<data.model.tv_detail.Genre> {
        val response = apiClient.get { url { encodedPath = "genre/tv/list" } }.body<GenreResponse>()
        return response.genres.map { data.model.tv_detail.Genre(it.id, it.name) }
    }
    override suspend fun moviesByGenre(genreId: Int, page: Int): BaseModel<MovieItem> = getPaginated<MovieItem>("discover/movie", page) { url -> url.parameters.append("with_genres", genreId.toString()) }
    override suspend fun tvSeriesByGenre(genreId: Int, page: Int): BaseModel<TvSeriesItem> = getPaginated<TvSeriesItem>("discover/tv", page) { url -> url.parameters.append("with_genres", genreId.toString()) }
}