package ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import constant.AppConstant
import data.model.tv_detail.Season
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.episodes_count
import kmp_movie.composeapp.generated.resources.seasons
import org.jetbrains.compose.resources.stringResource
import theme.Spacing

@Composable
fun SeasonsSection(
    seasons: List<Season>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(bottom = Spacing.sm)) {
        Text(
            modifier = Modifier
                .semantics { heading() }
                .padding(bottom = Spacing.sm),
            text = stringResource(Res.string.seasons),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(seasons, key = { it.id }) { season ->
                Column(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(end = Spacing.sm),
                ) {
                    ImageLoad(
                        url = AppConstant.imageUrl(season.posterPath, size = "w185"),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(MaterialTheme.shapes.small),
                    )
                    Text(
                        text = season.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = Spacing.xs),
                    )
                    Text(
                        text = stringResource(Res.string.episodes_count, season.episodeCount),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    if (!season.airDate.isNullOrBlank()) {
                        Text(
                            text = season.airDate,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}
