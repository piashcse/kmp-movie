package data.repository

import data.remote.ApiImpl
import kotlinx.coroutines.flow.flow
import utils.network.DataState

class MovieRepository {
    private val api = ApiImpl()
    fun nowPlayingMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.nowPlayingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun popularMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.popularMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun topRatedMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.topRatedMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun upComingMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.upcomingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun movieDetail(movieId: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.movieDetail(movieId)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}