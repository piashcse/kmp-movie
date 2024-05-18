package data.repository

import data.remote.ApiImpl
import kotlinx.coroutines.flow.flow
import utils.network.DataState

class MovieRepository {
    private val api = ApiImpl()
    suspend fun nowPlayingMovie(page: Int) = flow {
        try {
            emit(DataState.Loading)
            val result = api.nowPlayingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun popularMovie(page: Int) = flow {
        try {
            emit(DataState.Loading)
            val result = api.popularMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun topRatedMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.topRatedMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun upComingMovie(page: Int) = flow {
        try {
            emit(DataState.Loading)
            val result = api.upcomingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun movieDetail(movieId: Int) = flow {
        try {
            emit(DataState.Loading)
            val result = api.movieDetail(movieId)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun searchMovie(searchKey: String) = flow {
        try {
            emit(DataState.Loading)
            val result = api.movieSearch(searchKey)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}