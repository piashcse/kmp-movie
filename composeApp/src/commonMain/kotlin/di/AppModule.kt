package di

import data.remote.ApiService
import data.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ui.screens.AppViewModel
import ui.screens.artist_detail.ArtistDetailViewModel
import ui.screens.celebrities.popular.PopularCelebritiesViewModel
import ui.screens.celebrities.trending.TrendingCelebritiesViewModel
import ui.screens.movie.detail.MovieDetailViewModel
import ui.screens.movie.now_playing.NowPlayingViewModel
import ui.screens.movie.popular.PopularMovieViewModel
import ui.screens.movie.top_rated.TopRatedMovieViewModel
import ui.screens.movie.upcoming.UpcomingMovieViewModel
import ui.screens.tv_series.airing_today.AiringTodayTvSeriesViewModel
import ui.screens.tv_series.detail.TvSeriesDetailViewModel
import ui.screens.tv_series.on_the_air.OnTheAirTvSeriesViewModel
import ui.screens.tv_series.popular.PopularTvSeriesViewModel
import ui.screens.tv_series.top_rated.TopRatedTvSeriesViewModel

@OptIn(ExperimentalCoroutinesApi::class)
val appModule: Module = module {
    // Add your common dependencies here
    single { ApiService() }
    single { Repository(get()) }
    viewModel { AppViewModel(get()) }
    viewModel { ArtistDetailViewModel(get()) }
    viewModel { PopularCelebritiesViewModel(get()) }
    viewModel { TrendingCelebritiesViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { NowPlayingViewModel(get()) }
    viewModel { PopularMovieViewModel(get()) }
    viewModel { TopRatedMovieViewModel(get()) }
    viewModel { UpcomingMovieViewModel(get()) }
    viewModel { AiringTodayTvSeriesViewModel(get()) }
    viewModel { TvSeriesDetailViewModel(get()) }
    viewModel { OnTheAirTvSeriesViewModel(get()) }
    viewModel { PopularTvSeriesViewModel(get()) }
    viewModel { TopRatedTvSeriesViewModel(get()) }
}