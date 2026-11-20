package ui.screens.movie.detail

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
import data.model.movie_detail.MovieDetail
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.description
import kmp_movie.composeapp.generated.resources.duration
import kmp_movie.composeapp.generated.resources.language
import kmp_movie.composeapp.generated.resources.rating
import kmp_movie.composeapp.generated.resources.release_date
import kmp_movie.composeapp.generated.resources.similar_movie
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.component.ArtistAndCrewSection
import ui.component.CastMember
import ui.component.RecommendedSection
import ui.component.base.BaseColumn
import ui.screens.detail.DetailInfo
import ui.screens.detail.MediaDetailHeader
import utils.hourMinutes
import utils.roundTo

@Composable
fun MovieDetail(
    movieId: Int,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToArtist: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    BaseColumn(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface),
        onRetry = { viewModel.fetchMovieDetails(movieId) },
        loading = uiState.isLoading && uiState.movieDetail == null,
        errorMessage = uiState.errorMessage
    ) {
        uiState.movieDetail?.let { UiDetail(it, onBack) }
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            if (uiState.recommendedMovies.isNotEmpty()) {
                RecommendedSection(
                    title = stringResource(Res.string.similar_movie),
                    items = uiState.recommendedMovies,
                    getImagePath = { it.posterPath },
                    getItemId = { it.id },
                    onNavigateToDetail = onNavigateToDetail,
                )
            }
            uiState.movieCredit?.cast?.let { cast ->
                ArtistAndCrewSection(
                    cast = cast.map { CastMember(it.id, it.name, it.profilePath) },
                    onNavigateToArtist = onNavigateToArtist,
                )
            }
        }
    }
}

@Composable
fun UiDetail(data: MovieDetail, onBack: () -> Unit) {
    MediaDetailHeader(
        title = data.title,
        backdropUrl = AppConstant.imageUrl(data.backdropPath, size = "w780"),
        posterUrl = AppConstant.imageUrl(data.posterPath),
        info = listOf(
            DetailInfo(stringResource(Res.string.duration), data.runtime.hourMinutes()),
            DetailInfo(stringResource(Res.string.release_date), data.releaseDate),
            DetailInfo(stringResource(Res.string.language), data.originalLanguage),
            DetailInfo(stringResource(Res.string.rating), data.voteAverage.roundTo(1).toString()),
        ),
        descriptionLabel = stringResource(Res.string.description),
        overview = data.overview,
        favoriteItem = FavoriteItem(
            id = data.id,
            mediaType = MediaType.MOVIE,
            title = data.title,
            posterPath = data.posterPath,
            releaseDate = data.releaseDate,
        ),
        onBack = onBack,
    )
}
