package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import data.model.MovieItem
import data.model.TvItem
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.rating_
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import org.jetbrains.compose.resources.stringResource
import theme.DefaultBackgroundColor
import theme.FontColor
import theme.SecondaryFontColor
import utils.AppConstant
import utils.cornerRadius
import utils.roundTo

@Composable
fun SearchForMovie(
    navController: Navigator,
    searchData: List<MovieItem>,
    itemClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 350.dp) // define max height
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .background(color = DefaultBackgroundColor)
            .padding(top = 8.dp)

    ) {
        items(items = searchData, itemContent = { item ->
            Row(modifier = Modifier
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                .clickable {
                    itemClick.invoke()
                    navController.navigate(
                        NavigationScreen.MovieDetail.route.plus(
                            "/${item.id}"
                        )
                    )
                }) {
                CoilImage(
                    imageModel = {
                        AppConstant.IMAGE_URL.plus(
                            item.backdrop_path
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
                        .height(100.dp)
                        .width(80.dp).cornerRadius(8)
                        .shimmerBackground(RoundedCornerShape(5.dp)),
                )
                Column {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 4.dp
                        ),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = item.release_date,
                        color = FontColor,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "${stringResource(Res.string.rating_)} ${
                            item.vote_average.roundTo(
                                1
                            )
                        }",
                        color = SecondaryFontColor,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        })
    }
}

@Composable
fun SearchForTVSeries(
    navController: Navigator,
    searchData: List<TvItem>,
    itemClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 350.dp) // define max height
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .background(color = DefaultBackgroundColor)
            .padding(top = 8.dp)

    ) {
        items(items = searchData, itemContent = { item ->
            Row(modifier = Modifier
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                .clickable {
                    itemClick.invoke()
                    navController.navigate(
                        NavigationScreen.MovieDetail.route.plus(
                            "/${item.id}"
                        )
                    )
                }) {
                CoilImage(
                    imageModel = {
                        AppConstant.IMAGE_URL.plus(
                            item.backdropPath
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
                        .height(100.dp)
                        .width(80.dp).cornerRadius(8)
                        .shimmerBackground(RoundedCornerShape(5.dp)),
                )
                Column {
                    Text(
                        text = item.name,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 4.dp
                        ),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = item.firstAirDate,
                        color = FontColor,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "${stringResource(Res.string.rating_)} ${
                            item.voteAverage.roundTo(
                                1
                            )
                        }",
                        color = SecondaryFontColor,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        })
    }
}