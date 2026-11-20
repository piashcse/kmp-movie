package data.repository

import data.model.Genre
import data.remote.ApiService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import utils.flowDirect
import utils.flowWithResults
import utils.network.UiState
import utils.storage.LocalStorageManager

class Repository(
    private val api: ApiService,
    private val localStorageManager: LocalStorageManager
) {

    // Movie endpoints
    fun nowPlayingMovie(page: Int) = flowWithResults { api.nowPlayingMovies(page) }
    fun popularMovie(page: Int) = flowWithResults { api.popularMovies(page) }
    fun topRatedMovie(page: Int) = flowWithResults { api.topRatedMovies(page) }
    fun upComingMovie(page: Int) = flowWithResults { api.upcomingMovies(page) }
    fun movieDetail(movieId: Int) = flowDirect { api.movieDetail(movieId) }
    fun searchMovie(searchKey: String) = flowDirect { api.movieSearch(searchKey) }

    // TV Series endpoints
    fun airingTodayTvSeries(page: Int) = flowWithResults { api.airingTodayTvSeries(page) }
    fun onTheAirTvSeries(page: Int) = flowWithResults { api.onTheAirTvSeries(page) }
    fun popularTvSeries(page: Int) = flowWithResults { api.popularTvSeries(page) }
    fun topRatedTvSeries(page: Int) = flowWithResults { api.topRatedTvSeries(page) }
    fun tvSeriesDetail(seriesId: Int) = flowDirect { api.tvSeriesDetail(seriesId) }
    fun searchTvSeries(searchKey: String) = flowDirect { api.tvSeriesSearch(searchKey) }

    // Celebrity endpoints
    fun popularCelebrities(page: Int) = flowWithResults { api.popularCelebrity(page) }
    fun trendingCelebrities(page: Int) = flowWithResults { api.trendingCelebrity(page) }
    fun searchCelebrity(searchKey: String) = flowDirect { api.celebritySearch(searchKey) }
    fun artistDetail(personId: Int) = flowDirect { api.artistDetail(personId) }

    // Local preferences
    suspend fun getThemeMode(): String? = localStorageManager.getThemeMode()
    suspend fun setThemeMode(mode: String) = localStorageManager.saveThemeMode(mode)

    // Genre endpoints. The lists are static for a session, so cache them instead of
    // re-fetching from every list screen.
    private val genreMutex = Mutex()
    private val movieGenres = MutableStateFlow<List<Genre>?>(null)
    private val tvGenres = MutableStateFlow<List<Genre>?>(null)

    fun getMovieGenres(): Flow<UiState<List<Genre>>> = cachedGenres(movieGenres, api::movieGenres)
    fun getTvGenres(): Flow<UiState<List<Genre>>> = cachedGenres(tvGenres, api::tvGenres)

    private fun cachedGenres(
        cache: MutableStateFlow<List<Genre>?>,
        request: suspend () -> List<Genre>,
    ): Flow<UiState<List<Genre>>> = flow {
        cache.value?.let {
            emit(UiState.Success(it))
            return@flow
        }
        emit(UiState.Loading)
        genreMutex.withLock {
            val existing = cache.value
            if (existing != null) {
                emit(UiState.Success(existing))
            } else {
                try {
                    val data = request()
                    cache.value = data
                    emit(UiState.Success(data))
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    emit(UiState.Error(e))
                }
            }
        }
    }
    fun getMoviesByGenre(genreId: Int, page: Int) = flowWithResults<data.model.MovieItem> { api.moviesByGenre(genreId, page) }
    fun getTvSeriesByGenre(genreId: Int, page: Int) = flowWithResults<data.model.TvSeriesItem> { api.tvSeriesByGenre(genreId, page) }
}