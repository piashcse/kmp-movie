package di

import com.russhwolf.settings.Settings
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
import ui.screens.genre.GenreContentViewModel
import ui.screens.genre.GenreListViewModel
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
import utils.storage.LocalStorageManager
import utils.storage.SimpleInMemorySettings

@OptIn(ExperimentalCoroutinesApi::class)
val appModule: Module = module {
    // Core dependencies
    single<Settings> { SimpleInMemorySettings() }
    single { LocalStorageManager(get()) }
    single { ApiService() }
    single { Repository(api = get(), localStorageManager = get()) }

    // App-level ViewModels
    viewModel { AppViewModel(get()) }

    // Movie ViewModels
    viewModel { NowPlayingViewModel(get()) }
    viewModel { PopularMovieViewModel(get()) }
    viewModel { TopRatedMovieViewModel(get()) }
    viewModel { UpcomingMovieViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }

    // TV Series ViewModels
    viewModel { AiringTodayTvSeriesViewModel(get()) }
    viewModel { OnTheAirTvSeriesViewModel(get()) }
    viewModel { PopularTvSeriesViewModel(get()) }
    viewModel { TopRatedTvSeriesViewModel(get()) }
    viewModel { TvSeriesDetailViewModel(get()) }

    // Celebrity ViewModels
    viewModel { PopularCelebritiesViewModel(get()) }
    viewModel { TrendingCelebritiesViewModel(get()) }
    viewModel { ArtistDetailViewModel(get()) }

    // Genre ViewModels
    viewModel { GenreListViewModel(get()) }
    viewModel { GenreContentViewModel(get()) }
}