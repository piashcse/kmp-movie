package data.repository

import data.remote.ApiService
import kotlinx.coroutines.flow.flow
import utils.network.UiState

class Repository(private val api: ApiService = ApiService()) {
    fun nowPlayingMovie(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.nowPlayingMovies(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun popularMovie(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.popularMovies(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun topRatedMovie(page: Int) = flow {
        emit(UiState.Loading)
        try {
            val result = api.topRatedMovies(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun upComingMovie(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.upcomingMovies(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun movieDetail(movieId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.movieDetail(movieId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun searchMovie(searchKey: String) = flow {
        try {
            emit(UiState.Loading)
            val result = api.movieSearch(searchKey)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun recommendedMovie(movieId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.recommendedMovies(movieId)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun movieCredit(movieId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.movieCredit(movieId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun artistDetail(personId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.artistDetail(personId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun artistMoviesAndTvShows(personId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.artistMoviesAndTvSeries(personId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun airingTodayTvSeries(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.airingTodayTvSeries(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun onTheAirTvSeries(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.onTheAirTvSeries(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun popularTvSeries(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.popularTvSeries(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun topRatedTvSeries(page: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.topRatedTvSeries(page)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun tvSeriesDetail(seriesId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.tvSeriesDetail(seriesId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun recommendedTvSeries(seriesId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.recommendedTvSeries(seriesId)
            emit(UiState.Success(result.results))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun creditTvSeries(seriesId: Int) = flow {
        try {
            emit(UiState.Loading)
            val result = api.creditTvSeries(seriesId)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }

    fun searchTvSeries(searchKey: String) = flow {
        try {
            emit(UiState.Loading)
            val result = api.tvSeriesSearch(searchKey)
            emit(UiState.Success(result))
        } catch (e: Exception) {
            emit(UiState.Error(e))
        }
    }
}