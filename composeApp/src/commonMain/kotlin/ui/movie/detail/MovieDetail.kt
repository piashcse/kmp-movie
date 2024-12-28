package ui.movie.detail

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import data.model.MovieItem
import data.model.artist.Cast
import data.model.movie_detail.MovieDetail
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.cast
import kmp_movie.composeapp.generated.resources.description
import kmp_movie.composeapp.generated.resources.duration
import kmp_movie.composeapp.generated.resources.language
import kmp_movie.composeapp.generated.resources.rating
import kmp_movie.composeapp.generated.resources.release_date
import kmp_movie.composeapp.generated.resources.similar_movie
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import org.jetbrains.compose.resources.stringResource
import theme.DefaultBackgroundColor
import theme.FontColor
import theme.cornerRadius
import ui.component.ExpandableText
import ui.component.ProgressIndicator
import ui.component.shimmerBackground
import ui.component.text.SubtitlePrimary
import ui.component.text.SubtitleSecondary
import utils.AppConstant
import utils.hourMinutes
import utils.roundTo

@Composable
fun MovieDetail(
    navigator: Navigator,
    movieId: Int,
    viewModel: MovieDetailViewModel = viewModel { MovieDetailViewModel() }
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val movieDetail by viewModel.movieDetail.collectAsState()
    val recommendMovie by viewModel.recommendedMovie.collectAsState()
    val movieCredit by viewModel.movieCredit.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.movieDetail(movieId)
        viewModel.recommendedMovie(movieId)
        viewModel.movieCredit(movieId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(DefaultBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            ProgressIndicator()
        } else {
            movieDetail?.let { UiDetail(it) }
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                recommendMovie.takeIf { it.isNotEmpty() }?.let {
                    RecommendedMovie(navigator, it)
                }
                movieCredit?.cast?.let {
                    ArtistAndCrew(navigator, it)
                }
            }
        }
    }
}

@Composable
fun UiDetail(data: MovieDetail) {
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
        Column(modifier = Modifier.padding(start = 10.dp, top = 200.dp, end = 10.dp)) {
            Row {
                ImageLoad(
                    url = AppConstant.IMAGE_URL + data.posterPath,
                    modifier = Modifier
                        .size(135.dp, 180.dp)
                        .clip(RoundedCornerShape(10.dp))
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
                        text = data.title,
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
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            ExpandableText(text = data.overview, maxLines = 2)
        }
    }
}

@Composable
fun RecommendedMovie(navigator: Navigator, recommendedMovie: List<MovieItem>) {
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
                                navigator.navigate(
                                    NavigationScreen.MovieDetail.route.plus(
                                        "/${item.id}"
                                    )
                                )
                            }

                    )
                }
            }
        }
    }
}

@Composable
fun ArtistAndCrew(navigator: Navigator?, cast: List<Cast>) {
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
                        .padding(end = 10.dp)
                        .width(80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ImageLoad(
                        url = AppConstant.IMAGE_URL + item.profilePath,
                        modifier = Modifier.padding(bottom = 4.dp)
                            .size(80.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .clickable {
                                navigator?.navigate(
                                    NavigationScreen.ArtistDetail.route.plus("/${item.id}")
                                )
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
            contentDescription = "Image"
        ),
        component = rememberImageComponent {
            +CircularRevealPlugin(
                duration = 800
            )
        },
        modifier = modifier.shimmerBackground(RoundedCornerShape(5.dp)),
    )
}