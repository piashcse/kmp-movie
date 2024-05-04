package ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.component.text.SubtitlePrimary
import ui.component.text.SubtitleSecondary
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import data.model.moviedetail.MovieDetail
import moe.tlaster.precompose.navigation.Navigator
import theme.DefaultBackgroundColor
import theme.FontColor
import ui.component.ProgressIndicator
import ui.component.shimmerBackground
import utils.AppConstant
import utils.AppString
import utils.hourMinutes
import utils.network.DataState
import utils.roundTo

@Composable
fun MovieDetail(
    navigator: Navigator,
    movieId: Int,
    movieDetailViewModel: MovieDetailViewModel = MovieDetailViewModel()
) {
    val isLoading = remember { mutableStateOf(false) }
    val movieDetail = remember { mutableStateOf<MovieDetail?>(null) }

    LaunchedEffect(key1 = movieDetailViewModel) {
        movieDetailViewModel.movieDetail(movieId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DefaultBackgroundColor
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        movieDetail.value?.let {
            UiDetail(it)
        }
    }
    movieDetailViewModel.movieDetail.collectAsState().value?.let {
        when (it) {
            is DataState.Loading -> {
                isLoading.value = true
            }

            is DataState.Success<MovieDetail> -> {
                movieDetail.value = it.data
                isLoading.value = false
            }

            is DataState.Error -> {
                Text("Error :${it.exception}")
                isLoading.value = false
            }
        }
    }
}

@Composable
fun UiDetail(data: MovieDetail) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        CoilImage(
            imageModel = {
                AppConstant.IMAGE_URL.plus(
                    data.poster_path
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
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp).shimmerBackground(
                    RoundedCornerShape(5.dp)
                ),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = data.title,
                modifier = Modifier.padding(top = 10.dp),
                color = FontColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.W700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, top = 10.dp)
            ) {

                Column(Modifier.weight(1f)) {
                    SubtitlePrimary(
                        text = data.original_language,
                    )
                    SubtitleSecondary(
                        text = AppString.LANGUAGE
                    )
                }
                Column(Modifier.weight(1f)) {
                    SubtitlePrimary(
                        text = data.vote_average.roundTo(1).toString(),
                    )
                    SubtitleSecondary(
                        text = AppString.RATING
                    )
                }
                Column(Modifier.weight(1f)) {
                    SubtitlePrimary(
                        text = data.runtime.hourMinutes()
                    )
                    SubtitleSecondary(
                        text = AppString.DURATION
                    )
                }
                Column(Modifier.weight(1f)) {
                    SubtitlePrimary(
                        text = data.release_date
                    )
                    SubtitleSecondary(
                        text = AppString.RELEASE_DATE
                    )
                }
            }
            Text(
                text = AppString.DESCRIPTION,
                color = FontColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = data.overview)
        }
    }
}


