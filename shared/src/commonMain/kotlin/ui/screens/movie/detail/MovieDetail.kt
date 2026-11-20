package ui.screens.movie.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import constant.AppConstant
import data.model.MovieItem
import data.model.local.FavoriteItem
import data.model.local.MediaType
import data.model.movie_detail.MovieDetail
import data.repository.FavoritesStore
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.action_back
import kmp_movie.composeapp.generated.resources.description
import kmp_movie.composeapp.generated.resources.duration
import kmp_movie.composeapp.generated.resources.language
import kmp_movie.composeapp.generated.resources.rating
import kmp_movie.composeapp.generated.resources.release_date
import kmp_movie.composeapp.generated.resources.similar_movie
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import theme.cornerRadius
import ui.component.ArtistAndCrewSection
import ui.component.CastMember
import ui.component.ExpandableText
import ui.component.FavoriteButton
import ui.component.ImageLoad
import ui.component.base.BaseColumn
import ui.component.text.SubtitlePrimary
import ui.component.text.SubtitleSecondary
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
        loading = uiState.isLoading && uiState.movieDetail == null,
        errorMessage = uiState.errorMessage
    ) {
        uiState.movieDetail?.let { UiDetail(it, onBack) }
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            if (uiState.recommendedMovies.isNotEmpty()) {
                RecommendedMovie(uiState.recommendedMovies, onNavigateToDetail)
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
    Box {
        ImageLoad(
            url = AppConstant.imageUrl(data.backdropPath, size = "w780"),
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .graphicsLayer {
                    alpha = 0.8f
                    shadowElevation = 10f
                    renderEffect = BlurEffect(15f, 15f)
                }
        )
        
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            val favoritesStore = koinInject<FavoritesStore>()
            val favorites by favoritesStore.favorites.collectAsState()
            val isFavorite = favorites.any { it.id == data.id && it.mediaType == MediaType.MOVIE }

            FavoriteButton(
                isFavorite = isFavorite,
                onClick = {
                    favoritesStore.toggle(
                        FavoriteItem(
                            id = data.id,
                            mediaType = MediaType.MOVIE,
                            title = data.title,
                            posterPath = data.posterPath,
                            releaseDate = data.releaseDate,
                        )
                    )
                },
                modifier = Modifier.size(32.dp),
            )
        }

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.action_back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Column(modifier = Modifier.padding(start = 10.dp, top = 200.dp, end = 10.dp)) {
            Row {
                ImageLoad(
                    url = AppConstant.imageUrl(data.posterPath),
                    modifier = Modifier
                        .size(135.dp, 180.dp)
                        .cornerRadius(10)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = data.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Column(Modifier.weight(1f)) {
                            SubtitlePrimary(
                                text = stringResource(Res.string.duration)
                            )
                            SubtitleSecondary(
                                text = data.runtime.hourMinutes()
                            )
                        }
                        Column(Modifier.weight(1f)) {
                            SubtitlePrimary(
                                text = stringResource(Res.string.release_date)
                            )
                            SubtitleSecondary(
                                text = data.releaseDate
                            )
                        }
                    }
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        Column(Modifier.weight(1f)) {
                            SubtitlePrimary(
                                text = stringResource(Res.string.language)
                            )
                            SubtitleSecondary(
                                text = data.originalLanguage
                            )
                        }
                        Column(Modifier.weight(1f)) {
                            SubtitlePrimary(
                                text = stringResource(Res.string.rating)
                            )
                            SubtitleSecondary(
                                text = data.voteAverage.roundTo(1).toString()
                            )
                        }
                    }

                }
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(Res.string.description),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            ExpandableText(text = data.overview, maxLines = 2)
        }
    }
}

@Composable
fun RecommendedMovie(recommendedMovie: List<MovieItem>, onNavigateToDetail: (Int) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(Res.string.similar_movie),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(recommendedMovie, key = { it.id }) { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 8.dp, top = 5.dp, bottom = 5.dp
                    )
                ) {
                    ImageLoad(
                        url = AppConstant.imageUrl(item.posterPath),
                        modifier = Modifier
                            .size(135.dp, 180.dp)
                            .cornerRadius(10)
                            .clickable {
                                onNavigateToDetail(item.id)
                            }

                    )
                }
            }
        }
    }
}

