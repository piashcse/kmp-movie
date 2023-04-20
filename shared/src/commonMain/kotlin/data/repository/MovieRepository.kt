package data.repository

import data.remote.MovieDetailApiImpl
import data.remote.NowPlayingMovieApiImpl
import kotlinx.coroutines.flow.flow
import utils.network.DataState

class MovieRepository {
    private val nowPlayingApi = NowPlayingMovieApiImpl()
    private val movieDetail = MovieDetailApiImpl()
    fun nowPlayingView(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = nowPlayingApi.nowPlayingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun movieDetail(movieId: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = movieDetail.movieDetail(movieId)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}