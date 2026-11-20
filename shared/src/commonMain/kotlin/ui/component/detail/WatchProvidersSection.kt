package ui.component.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import constant.AppConstant
import data.model.watch_provider.WatchProvider
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.where_to_watch
import org.jetbrains.compose.resources.stringResource
import theme.Spacing
import ui.component.ImageLoad

/**
 * Streaming/rental/purchase providers for the detected region. Tapping a logo opens the TMDB
 * watch page for that region, which is the canonical list of deep links for each service.
 */
@Composable
fun WatchProvidersSection(
    providers: List<WatchProvider>,
    link: String?,
    modifier: Modifier = Modifier,
) {
    if (providers.isEmpty()) return

    val uriHandler = LocalUriHandler.current

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .semantics { heading() }
                .padding(horizontal = Spacing.md),
            text = stringResource(Res.string.where_to_watch),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = Spacing.md,
                end = Spacing.md,
                top = Spacing.sm,
                bottom = Spacing.sm,
            ),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            items(providers, key = { it.providerId }) { provider ->
                Column(
                    modifier = Modifier
                        .width(76.dp)
                        .clickable(
                            enabled = link != null,
                            onClickLabel = provider.providerName,
                        ) {
                            link?.let { runCatching { uriHandler.openUri(it) } }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ImageLoad(
                        url = AppConstant.imageUrl(provider.logoPath, size = "w92"),
                        modifier = Modifier
                            .size(48.dp)
                            .clip(MaterialTheme.shapes.small),
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Text(
                        text = provider.providerName,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
