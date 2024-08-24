package data.repository

import data.remote.ApiImpl
import kotlinx.coroutines.flow.flow
import utils.network.UiState

class MovieRepository(private val api:ApiImpl =  ApiImpl()) {
    suspend fun nowPlayingMovie(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.nowPlayingMovieList(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun popularMovie(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.popularMovieList(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun topRatedMovie(page: Int) = flow {
        emit(UiState.Loading)
        try {
            val result = api.topRatedMovieList(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun upComingMovie(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.upcomingMovieList(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun movieDetail(movieId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.movieDetail(movieId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun searchMovie(searchKey: String) = flow {
        try {
            emit(UiState.Loading)
            val result = api.movieSearch(searchKey)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun recommendedMovie(movieId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.recommendedMovie(movieId)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }
    suspend fun movieCredit(movieId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.movieCredit(movieId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }
    suspend fun artistDetail(personId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.artistDetail(personId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun airingTodayTvSeries(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.airingTodayTvSeries(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun onTheAirTvSeries(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.onTheAirTvSeries(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun topRatedTvSeries(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.popularTvSeries(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun upcomingTvSeries(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.topRatedTvSeries(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun tvSeriesDetail(seriesId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.tvSeriesDetail(seriesId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun recommendedTvSeries(seriesId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.recommendedTvSeries(seriesId)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun creditTvSeries(seriesId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.creditTvSeries(seriesId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    suspend fun searchTvSeries(searchKey: String) = flow {
        try {
            emit(UiState.Loading)
            val result = api.tvSeriesSearch(searchKey)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }
}