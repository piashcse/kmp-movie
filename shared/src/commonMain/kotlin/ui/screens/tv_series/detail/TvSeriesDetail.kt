package ui.screens.tv_series.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import constant.AppConstant
import data.model.local.FavoriteItem
import data.model.local.MediaType
import data.model.tv_detail.TvSeriesDetail
import data.model.video.bestTrailer
import data.model.video.watchUrl
import data.model.watch_provider.deviceRegionCode
import data.model.watch_provider.regionFor
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.description
import kmp_movie.composeapp.generated.resources.first_air
import kmp_movie.composeapp.generated.resources.language
import kmp_movie.composeapp.generated.resources.number_of_episode
import kmp_movie.composeapp.generated.resources.rating
import kmp_movie.composeapp.generated.resources.similar_movie
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.component.ArtistAndCrewSection
import ui.component.CastMember
import ui.component.RecommendedSection
import ui.component.SeasonsSection
import ui.component.base.BaseColumn
import ui.component.detail.DetailActions
import ui.component.detail.WatchProvidersSection
import ui.component.skeleton.MediaDetailSkeleton
import ui.screens.detail.DetailInfo
import ui.screens.detail.MediaDetailHeader
import utils.roundTo

@Composable
fun TvSeriesDetail(
    seriesId: Int,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToArtist: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TvSeriesDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(seriesId) {
        viewModel.fetchTvSeriesDetails(seriesId)
    }

    BaseColumn(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface),
        onRetry = { viewModel.fetchTvSeriesDetails(seriesId) },
        loadingContent = { MediaDetailSkeleton() },
        loading = uiState.isLoading && uiState.tvSeriesDetail == null,
        errorMessage = uiState.errorMessage
    ) {
        uiState.tvSeriesDetail?.let { data ->
            UiDetail(data, onBack)
            DetailActions(
                title = data.name,
                shareUrl = "https://www.themoviedb.org/tv/${data.id}",
                trailerUrl = data.videos?.results.orEmpty().bestTrailer()?.watchUrl(),
            )
            val region = data.watchProviders?.regionFor(deviceRegionCode())
            if (region != null) {
                WatchProvidersSection(providers = region.providers, link = region.link)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            uiState.tvSeriesDetail?.seasons?.takeIf { it.isNotEmpty() }?.let { seasons ->
                SeasonsSection(seasons = seasons)
            }
            if (uiState.recommendedTvSeries.isNotEmpty()) {
                RecommendedSection(
                    title = stringResource(Res.string.similar_movie),
                    items = uiState.recommendedTvSeries,
                    getImagePath = { it.posterPath },
                    getItemId = { it.id },
                    onNavigateToDetail = onNavigateToDetail,
                )
            }
            uiState.creditTvSeries?.cast?.let { cast ->
                ArtistAndCrewSection(
                    cast = cast.map { CastMember(it.id, it.name, it.profilePath) },
                    onNavigateToArtist = onNavigateToArtist,
                )
            }
        }
    }
}

@Composable
fun UiDetail(data: TvSeriesDetail, onBack: () -> Unit) {
    MediaDetailHeader(
        title = data.name,
        backdropUrl = AppConstant.imageUrl(data.backdropPath, size = "w780"),
        posterUrl = AppConstant.imageUrl(data.posterPath),
        info = listOf(
            DetailInfo(stringResource(Res.string.number_of_episode), data.numberOfEpisodes.toString()),
            DetailInfo(stringResource(Res.string.first_air), data.firstAirDate),
            DetailInfo(stringResource(Res.string.language), data.originalLanguage),
            DetailInfo(stringResource(Res.string.rating), data.voteAverage.roundTo(1).toString()),
        ),
        descriptionLabel = stringResource(Res.string.description),
        overview = data.overview,
        favoriteItem = FavoriteItem(
            id = data.id,
            mediaType = MediaType.TV,
            title = data.name,
            posterPath = data.posterPath,
            releaseDate = data.firstAirDate,
        ),
        onBack = onBack,
    )
}
