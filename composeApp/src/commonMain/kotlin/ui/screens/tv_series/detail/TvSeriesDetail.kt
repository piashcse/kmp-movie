package ui.screens.tv_series.detail

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import constant.AppConstant
import data.model.TvSeriesItem
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Cast
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.cast
import kmp_movie.composeapp.generated.resources.description
import kmp_movie.composeapp.generated.resources.first_air
import kmp_movie.composeapp.generated.resources.language
import kmp_movie.composeapp.generated.resources.number_of_episode
import kmp_movie.composeapp.generated.resources.rating
import kmp_movie.composeapp.generated.resources.similar_movie
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import theme.DefaultBackgroundColor
import theme.FontColor
import theme.cornerRadius
import ui.component.ExpandableText
import ui.component.base.BaseColumn
import ui.component.shimmerBackground
import ui.component.text.SubtitlePrimary
import ui.component.text.SubtitleSecondary
import utils.roundTo

@Composable
fun TvSeriesDetail(
    seriesId: Int,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToArtist: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: TvSeriesDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(seriesId) {
        viewModel.fetchTvSeriesDetails(seriesId)
    }

    BaseColumn(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(DefaultBackgroundColor),
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        uiState.tvSeriesDetail?.let { UiDetail(it, onBack) }
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            uiState.recommendedTvSeries.takeIf { it.isNotEmpty() }?.let {
                RecommendedTVSeries(it, onNavigateToDetail)
            }
            uiState.creditTvSeries?.cast?.let {
                ArtistAndCrew(it, onNavigateToArtist)
            }
        }
    }
}

@Composable
fun UiDetail(data: TvSeriesDetail, onBack: () -> Unit) {
    Box {
        ImageLoad(
            url = AppConstant.IMAGE_URL + data.backdropPath,
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .graphicsLayer {
                    alpha = 0.8f
                    shadowElevation = 10f
                    renderEffect = BlurEffect(15f, 15f)
                }
                .shimmerBackground(RoundedCornerShape(5.dp))
        )
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Column(modifier = Modifier.padding(start = 10.dp, top = 200.dp, end = 10.dp)) {
            Row {
                ImageLoad(
                    url = AppConstant.IMAGE_URL + data.posterPath,
                    modifier = Modifier
                        .size(135.dp, 180.dp)
                        .cornerRadius(10)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                        .shimmerBackground(RoundedCornerShape(5.dp))
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = data.name,
                        color = FontColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Column(Modifier.weight(1f)) {
                            SubtitlePrimary(
                                text = stringResource(Res.string.number_of_episode)
                            )
                            SubtitleSecondary(
                                text = data.numberOfEpisodes.toString()
                            )
                        }
                        Column(Modifier.weight(1f)) {
                            SubtitlePrimary(
                                text = stringResource(Res.string.first_air)
                            )
                            SubtitleSecondary(
                                text = data.firstAirDate
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
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            ExpandableText(text = data.overview, maxLines = 2)
        }
    }
}

@Composable
fun RecommendedTVSeries(recommendedMovie: List<TvSeriesItem>, onNavigateToDetail: (Int) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(Res.string.similar_movie),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(recommendedMovie) { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 8.dp, top = 5.dp, bottom = 5.dp
                    )
                ) {
                    ImageLoad(
                        url = AppConstant.IMAGE_URL + item.posterPath,
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

@Composable
fun ArtistAndCrew(cast: List<Cast>, onNavigateToArtist: (Int) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(Res.string.cast),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(cast) { item ->
                Column(
                    modifier = Modifier
                        .padding(end = 10.dp, bottom = 4.dp)
                        .width(80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ImageLoad(
                        url = AppConstant.IMAGE_URL + item.profilePath,
                        modifier = Modifier.padding(bottom = 4.dp)
                            .size(80.dp)
                            .cornerRadius(40)
                            .clickable {
                                onNavigateToArtist(item.id)
                            }
                    )
                    SubtitleSecondary(text = item.name)
                }
            }
        }
    }
}

@Composable
fun ImageLoad(url: String, modifier: Modifier = Modifier) {
    CoilImage(
        imageModel = { url },
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        ),
        component = rememberImageComponent {
            +CircularRevealPlugin(
                duration = 800
            )
        },
        modifier = modifier.shimmerBackground(RoundedCornerShape(5.dp)),
    )
}