package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import constant.AppConstant
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.celebrities.Celebrity
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.rating_
import org.jetbrains.compose.resources.stringResource
import theme.DefaultBackgroundColor
import theme.FontColor
import theme.SecondaryFontColor
import utils.cornerRadius
import utils.roundTo

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFE0E0E0),
            contentColor = if (isSelected) Color.White else FontColor
        ),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .width(110.dp)
            .height(40.dp),
        shape = RoundedCornerShape(20.dp) // More rounded corners
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    modifier = Modifier.padding(end = 4.dp).width(16.dp).height(16.dp)
                )
            }
            Text(
                text = text,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SearchResults(
    movieSearchData: List<MovieItem>,
    tvSeriesSearchData: List<TvSeriesItem>,
    celebritySearchData: List<Celebrity>,
    currentFilter: String = "Movies",
    onFilterChange: (String) -> Unit = {},
    onResultClick: () -> Unit,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTvSeries: (Int) -> Unit,
    onNavigateToCelebrity: (Int) -> Unit
) {
    var selectedFilter by remember { mutableStateOf(currentFilter) }
    
    // Update the selected filter when currentFilter changes
    if (selectedFilter != currentFilter) {
        selectedFilter = currentFilter
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 600.dp)
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .background(color = DefaultBackgroundColor)
    ) {
        // Filter buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            FilterButton(
                text = "Movies",
                isSelected = selectedFilter == "Movies",
                onClick = { 
                    selectedFilter = "Movies"
                    onFilterChange("Movies")
                }
            )
            FilterButton(
                text = "TV Series",
                isSelected = selectedFilter == "TV Series",
                onClick = { 
                    selectedFilter = "TV Series"
                    onFilterChange("TV Series")
                }
            )
            FilterButton(
                text = "Celebrities",
                isSelected = selectedFilter == "Celebrities",
                onClick = { 
                    selectedFilter = "Celebrities"
                    onFilterChange("Celebrities")
                }
            )
        }
        
        // Search results based on filter
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            when (selectedFilter) {
                "Movies" -> {
                    items(items = movieSearchData.take(10), itemContent = { item ->
                        SearchMovieItem(
                            item = item,
                            onResultClick = onResultClick,
                            onNavigateToMovie = onNavigateToMovie
                        )
                    })
                }
                "TV Series" -> {
                    items(items = tvSeriesSearchData.take(10), itemContent = { item ->
                        SearchTvSeriesItem(
                            item = item,
                            onResultClick = onResultClick,
                            onNavigateToTvSeries = onNavigateToTvSeries
                        )
                    })
                }
                "Celebrities" -> {
                    items(items = celebritySearchData.take(10), itemContent = { item ->
                        SearchCelebrityItem(
                            item = item,
                            onResultClick = onResultClick,
                            onNavigateToCelebrity = onNavigateToCelebrity
                        )
                    })
                }
                else -> {
                    // Default to movies
                    items(items = movieSearchData.take(10), itemContent = { item ->
                        SearchMovieItem(
                            item = item,
                            onResultClick = onResultClick,
                            onNavigateToMovie = onNavigateToMovie
                        )
                    })
                }
            }
        }
    }
}

@Composable
fun SearchMovieItem(
    item: MovieItem,
    onResultClick: () -> Unit,
    onNavigateToMovie: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
            .clickable {
                onResultClick.invoke()
                onNavigateToMovie(item.id)
            }
    ) {
        CoilImage(
            imageModel = {
                AppConstant.IMAGE_URL.plus(
                    item.backdropPath ?: item.posterPath ?: ""
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
                .height(120.dp)
                .width(100.dp)
                .cornerRadius(8)
                .shimmerBackground(RoundedCornerShape(5.dp)),
        )
        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text(
                text = item.title,
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = FontColor
            )
            Text(
                text = item.releaseDate,
                color = SecondaryFontColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "${stringResource(Res.string.rating_)} ${
                    item.voteAverage.roundTo(
                        1
                    )
                }",
                color = SecondaryFontColor,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun SearchTvSeriesItem(
    item: TvSeriesItem,
    onResultClick: () -> Unit,
    onNavigateToTvSeries: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
            .clickable {
                onResultClick.invoke()
                onNavigateToTvSeries(item.id)
            }
    ) {
        CoilImage(
            imageModel = {
                AppConstant.IMAGE_URL.plus(
                    item.backdropPath ?: item.posterPath ?: ""
                )
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "TV Series item",
                colorFilter = null,
            ),
            component = rememberImageComponent {
                +CircularRevealPlugin(
                    duration = 800
                )
            },
            modifier = Modifier
                .height(120.dp)
                .width(100.dp)
                .cornerRadius(8)
                .shimmerBackground(RoundedCornerShape(5.dp)),
        )
        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text(
                text = item.name,
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = FontColor
            )
            Text(
                text = item.firstAirDate,
                color = SecondaryFontColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "${stringResource(Res.string.rating_)} ${
                    item.voteAverage.roundTo(
                        1
                    )
                }",
                color = SecondaryFontColor,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun SearchCelebrityItem(
    item: Celebrity,
    onResultClick: () -> Unit,
    onNavigateToCelebrity: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
            .clickable {
                onResultClick.invoke()
                onNavigateToCelebrity(item.id)
            }
    ) {
        CoilImage(
            imageModel = {
                AppConstant.IMAGE_URL.plus(
                    item.profilePath ?: ""
                )
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Celebrity item",
                colorFilter = null,
            ),
            component = rememberImageComponent {
                +CircularRevealPlugin(
                    duration = 800
                )
            },
            modifier = Modifier
                .height(120.dp)
                .width(100.dp)
                .cornerRadius(8)
                .shimmerBackground(RoundedCornerShape(5.dp)),
        )
        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text(
                text = item.name,
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = FontColor
            )
            Text(
                text = item.knownForDepartment ?: "",
                color = SecondaryFontColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "Popularity: ${item.popularity.roundTo(1)}",
                color = SecondaryFontColor,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}