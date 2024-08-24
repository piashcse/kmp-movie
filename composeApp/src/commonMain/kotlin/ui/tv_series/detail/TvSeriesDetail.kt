package ui.tv_series.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import data.model.TvItem
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

@Composable
fun TvSeriesDetail(
    navigator: Navigator,
    seriesId: Int,
    viewModel: TvSeriesDetailViewModel = viewModel { TvSeriesDetailViewModel() }
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val movieDetail by viewModel.tvSeriesDetail.collectAsState()
    val recommendedTvSeries by viewModel.recommendedTvSeries.collectAsState()
    val creditTvSeries by viewModel.creditTvSeries.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.tvSeriesDetail(seriesId)
        viewModel.recommendedTvSeries(seriesId)
        viewModel.creditTvSeries(seriesId)
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(
            DefaultBackgroundColor
        ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            ProgressIndicator()
        } else {
            movieDetail?.let {
                UiDetail(it)
            }
            Column(
                modifier = Modifier.padding(
                    start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp
                )
            ) {
                recommendedTvSeries.let {
                    if (it.isNotEmpty())
                        RecommendedTvSeries(navigator, it)
                }
                creditTvSeries?.let {
                    credit(navigator, it.cast)
                }
            }
        }
    }
}

@Composable
fun UiDetail(data: TvSeriesDetail) {
    Column {
        CoilImage(
            imageModel = {
                AppConstant.IMAGE_URL.plus(
                    data.posterPath
                )
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Movie item",
                colorFilter = null,
            ),
            component = rememberImageComponent {
                +CircularRevealPlugin(
                    duration = 800
                )
            },
            modifier = Modifier.fillMaxWidth().height(300.dp).shimmerBackground(
                RoundedCornerShape(5.dp)
            ),
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = data.name,
                modifier = Modifier.padding(top = 10.dp),
                color = FontColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.W700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp, top = 10.dp)
            ) {

                Column(Modifier.weight(1f)) {
                    SubtitlePrimary(
                        text = data.originalLanguage,
                    )
                    SubtitleSecondary(
                        text =  stringResource(Res.string.language)
                    )
                }
                Column(Modifier.weight(1f)) {
                    SubtitlePrimary(
                        text = data.voteAverage.toString(),
                    )
                    SubtitleSecondary(
                        text =  stringResource(Res.string.rating)
                    )
                }
                Column(Modifier.weight(1f)) {
                    SubtitlePrimary(
                        text = data.numberOfEpisodes.toString()
                    )
                    SubtitleSecondary(
                        text =  stringResource(Res.string.number_of_episode)
                    )
                }
                Column(Modifier.weight(1f)) {
                    SubtitlePrimary(
                        text = data.firstAirDate
                    )
                    SubtitleSecondary(
                        text =  stringResource(Res.string.first_air)
                    )
                }
            }
            Text(
                text = stringResource(Res.string.description),
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            ExpandableText(text = data.overview)
        }
    }
}


@Composable
fun RecommendedTvSeries(navigator: Navigator, recommendedMovie: List<TvItem>) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(Res.string.similar_movie),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(recommendedMovie, itemContent = { item: TvItem ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 8.dp, top = 5.dp, bottom = 5.dp
                    )
                ) {
                    CoilImage(
                        modifier = Modifier.height(190.dp).width(140.dp).cornerRadius(10)
                            .clickable {
                                navigator.navigate(NavigationScreen.TvSeriesDetail.route.plus("/${item.id}"))
                            },
                        imageModel = { AppConstant.IMAGE_URL.plus(item.posterPath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "Similar movie",
                            colorFilter = null,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(
                                duration = 800
                            )
                        },
                    )
                }
            })

        }
    }
}

@Composable
fun credit(navigator: Navigator?, cast: List<Cast>) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(Res.string.cast),
            color = FontColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(cast, itemContent = { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 10.dp, top = 5.dp, bottom = 5.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoilImage(
                        modifier = Modifier.padding(bottom = 5.dp).height(80.dp).width(80.dp)
                            .cornerRadius(40).clickable {
                                navigator?.navigate(
                                    NavigationScreen.ArtistDetail.route.plus(
                                        "/${item.id}"
                                    )
                                )
                            },
                        imageModel = { AppConstant.IMAGE_URL.plus(item.profilePath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "artist and crew",
                            colorFilter = null,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(
                                duration = 800
                            )
                        },
                    )
                    SubtitleSecondary(text = item.name)
                }
            })
        }
    }
}
