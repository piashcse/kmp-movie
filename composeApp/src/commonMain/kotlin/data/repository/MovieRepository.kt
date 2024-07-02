package data.repository

import data.remote.ApiImpl
import kotlinx.coroutines.flow.flow
import utils.network.UiState

class MovieRepository {
    private val api = ApiImpl()
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
}