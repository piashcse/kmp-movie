package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import constant.AppConstant
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.celebrities.Celebrity
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.celebrities
import kmp_movie.composeapp.generated.resources.movies
import kmp_movie.composeapp.generated.resources.no_results_for
import kmp_movie.composeapp.generated.resources.rating_
import kmp_movie.composeapp.generated.resources.search_prompt
import kmp_movie.composeapp.generated.resources.tv_series
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import utils.roundTo

/**
 * The three searchable content types. Using an enum instead of raw strings keeps the
 * filter, the ViewModel call and the UI copy in sync.
 */
enum class SearchFilter(val labelRes: StringResource) {
    MOVIES(Res.string.movies),
    TV_SERIES(Res.string.tv_series),
    CELEBRITIES(Res.string.celebrities),
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .heightIn(min = 40.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .width(16.dp)
                        .height(16.dp),
                )
            }
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun SearchResults(
    movieSearchData: List<MovieItem>,
    tvSeriesSearchData: List<TvSeriesItem>,
    celebritySearchData: List<Celebrity>,
    currentFilter: SearchFilter = SearchFilter.MOVIES,
    onFilterChange: (SearchFilter) -> Unit = {},
    query: String = "",
    isLoading: Boolean = false,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTvSeries: (Int) -> Unit,
    onNavigateToCelebrity: (Int) -> Unit,
) {
    val movies = remember(movieSearchData) { movieSearchData.take(MAX_SEARCH_RESULTS) }
    val tvSeries = remember(tvSeriesSearchData) { tvSeriesSearchData.take(MAX_SEARCH_RESULTS) }
    val celebrities = remember(celebritySearchData) { celebritySearchData.take(MAX_SEARCH_RESULTS) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 600.dp)
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .background(color = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            SearchFilter.entries.forEach { filter ->
                FilterButton(
                    text = stringResource(filter.labelRes),
                    isSelected = currentFilter == filter,
                    onClick = { onFilterChange(filter) },
                )
            }
        }

        val currentItems = when (currentFilter) {
            SearchFilter.MOVIES -> movies
            SearchFilter.TV_SERIES -> tvSeries
            SearchFilter.CELEBRITIES -> celebrities
        }

        when {
            query.isBlank() -> SearchMessage(stringResource(Res.string.search_prompt))
            !isLoading && currentItems.isEmpty() ->
                SearchMessage(stringResource(Res.string.no_results_for, query))

            else -> LazyColumn(modifier = Modifier.fillMaxWidth()) {
                when (currentFilter) {
                    SearchFilter.MOVIES -> items(items = movies, key = { it.id }) { item ->
                        SearchMovieItem(item = item, onNavigateToMovie = onNavigateToMovie)
                    }

                    SearchFilter.TV_SERIES -> items(items = tvSeries, key = { it.id }) { item ->
                        SearchTvSeriesItem(item = item, onNavigateToTvSeries = onNavigateToTvSeries)
                    }

                    SearchFilter.CELEBRITIES -> items(items = celebrities, key = { it.id }) { item ->
                        SearchCelebrityItem(item = item, onNavigateToCelebrity = onNavigateToCelebrity)
                    }
                }
            }
        }
    }
}

private const val MAX_SEARCH_RESULTS = 10

@Composable
private fun SearchMessage(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun SearchMovieItem(
    item: MovieItem,
    onNavigateToMovie: (Int) -> Unit,
) {
    SearchResultRow(
        imagePath = item.backdropPath ?: item.posterPath,
        title = item.title,
        subtitle = item.releaseDate,
        meta = ratingText(item.voteAverage),
        contentDescription = item.title,
        onClick = { onNavigateToMovie(item.id) },
    )
}

@Composable
fun SearchTvSeriesItem(
    item: TvSeriesItem,
    onNavigateToTvSeries: (Int) -> Unit,
) {
    SearchResultRow(
        imagePath = item.backdropPath ?: item.posterPath,
        title = item.name,
        subtitle = item.firstAirDate,
        meta = ratingText(item.voteAverage),
        contentDescription = item.name,
        onClick = { onNavigateToTvSeries(item.id) },
    )
}

@Composable
fun SearchCelebrityItem(
    item: Celebrity,
    onNavigateToCelebrity: (Int) -> Unit,
) {
    SearchResultRow(
        imagePath = item.profilePath,
        title = item.name,
        subtitle = item.knownForDepartment.orEmpty(),
        meta = item.popularity.roundTo(1).toString(),
        contentDescription = item.name,
        onClick = { onNavigateToCelebrity(item.id) },
    )
}

@Composable
private fun SearchResultRow(
    imagePath: String?,
    title: String,
    subtitle: String,
    meta: String,
    contentDescription: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
            .clickable(onClick = onClick),
    ) {
        CoilImage(
            imageModel = { AppConstant.imageUrl(imagePath, size = "w185") },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = contentDescription,
                colorFilter = null,
            ),
            modifier = Modifier
                .height(120.dp)
                .width(100.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clip(RoundedCornerShape(8.dp)),
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = title,
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = meta,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}

@Composable
private fun ratingText(voteAverage: Double): String =
    "${stringResource(Res.string.rating_)} ${voteAverage.roundTo(1)}"
