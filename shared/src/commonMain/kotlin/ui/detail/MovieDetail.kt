package ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.component.text.SubtitlePrimary
import ui.component.text.SubtitleSecondary
import com.seiko.imageloader.rememberAsyncImagePainter
import data.model.moviedetail.MovieDetail
import moe.tlaster.precompose.navigation.Navigator
import theme.DefaultBackgroundColor
import theme.FontColor
import ui.component.ProgressIndicator
import utils.AppConstant
import utils.hourMinutes
import utils.network.DataState
import utils.roundTo

@Composable
fun MovieDetail(navigator: Navigator, movieId:Int, movieDetailViewModel: MovieDetailViewModel = MovieDetailViewModel()){
    LaunchedEffect(key1 = 1){
        movieDetailViewModel.movieDetail(movieId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DefaultBackgroundColor
            )
    ) {
        movieDetailViewModel.movieDetail.collectAsState().value.let {
            when (it) {
                is DataState.Loading -> {
                    ProgressIndicator()
                }

                is DataState.Success<MovieDetail> -> {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                AppConstant.IMAGE_URL.plus(
                                    it.data.poster_path
                                )
                            ),
                            contentDescription = it.data.poster_path,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp)
                        ) {
                            Text(
                                text = it.data.title,
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
                                        text = it.data.original_language,
                                    )
                                    SubtitleSecondary(
                                        text = "Language"
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    SubtitlePrimary(
                                        text = it.data.vote_average.roundTo(1).toString(),
                                    )
                                    SubtitleSecondary(
                                        text = "Rating"
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    SubtitlePrimary(
                                        text = it.data.runtime.hourMinutes()
                                    )
                                    SubtitleSecondary(
                                        text = "Duration"
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    SubtitlePrimary(
                                        text = it.data.release_date
                                    )
                                    SubtitleSecondary(
                                        text = "Release Date"
                                    )
                                }
                            }
                            Text(
                                text = "Description",
                                color = FontColor,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(text = it.data.overview)
                        }
                    }
                }
                else -> {
                }
            }
        }
    }
}

