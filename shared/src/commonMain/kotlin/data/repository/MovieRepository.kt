package data.repository

import data.remote.moviedetail.MovieDetailApiImpl
import data.remote.nowplaying.NowPlayingMovieApiImpl
import data.remote.pupular.PopularMovieApiImpl
import data.remote.toprated.TopRatedMovieApiImpl
import data.remote.upcoming.UpcomingMovieApiImpl
import kotlinx.coroutines.flow.flow
import utils.network.DataState

class MovieRepository {
    private val nowPlayingApi = NowPlayingMovieApiImpl()
    private val popularMovieApi = PopularMovieApiImpl()
    private val topRatedMovieApi = TopRatedMovieApiImpl()
    private val upcomingMovieApi = UpcomingMovieApiImpl()
    private val movieDetail = MovieDetailApiImpl()
    fun nowPlayingMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = nowPlayingApi.nowPlayingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun popularMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = popularMovieApi.popularMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun topRatedMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = topRatedMovieApi.topRatedMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun upComingMovie(page: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = upcomingMovieApi.upcomingMovieList(page)
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