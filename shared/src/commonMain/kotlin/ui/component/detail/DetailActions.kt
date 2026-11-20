package ui.component.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import data.model.video.VideoResults
import data.model.video.bestTrailer
import data.model.video.watchUrl
import data.model.watch_provider.WatchProviders
import data.model.watch_provider.deviceRegionCode
import data.model.watch_provider.regionFor
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.link_copied
import kmp_movie.composeapp.generated.resources.share
import kmp_movie.composeapp.generated.resources.watch_trailer
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import theme.Spacing
import utils.rememberShareLauncher

/**
 * Primary buttons for a movie/series detail page: play the trailer when one exists and share
 * (or copy on platforms without a share sheet) a canonical link.
 */
@Composable
private fun DetailActionButtons(
    title: String,
    shareUrl: String,
    trailerUrl: String?,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val shareLauncher = rememberShareLauncher()
    var linkCopied by remember { mutableStateOf(false) }

    LaunchedEffect(linkCopied) {
        if (linkCopied) {
            delay(2000)
            linkCopied = false
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        if (trailerUrl != null) {
            Button(
                onClick = { runCatching { uriHandler.openUri(trailerUrl) } },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm),
            ) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(Spacing.sm))
                Text(
                    text = stringResource(Res.string.watch_trailer),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        OutlinedButton(
            onClick = { linkCopied = shareLauncher.share(title, shareUrl) },
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm),
        ) {
            Icon(imageVector = Icons.Filled.Share, contentDescription = null)
            Spacer(modifier = Modifier.width(Spacing.sm))
            Text(
                text = stringResource(
                    if (linkCopied) Res.string.link_copied else Res.string.share
                ),
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * Trailer/share actions plus the region's watch providers, derived once per loaded detail.
 */
@Composable
fun MediaDetailActions(
    title: String,
    shareUrl: String,
    videos: VideoResults?,
    watchProviders: WatchProviders?,
    modifier: Modifier = Modifier,
) {
    val trailerUrl = remember(videos) { videos?.results.orEmpty().bestTrailer()?.watchUrl() }
    val region = remember(watchProviders) {
        watchProviders?.regionFor(deviceRegionCode())
    }
    // Keep the merged list stable across recompositions (e.g. while the detail page scrolls).
    val providers = remember(region) { region?.providers.orEmpty() }

    DetailActionButtons(
        title = title,
        shareUrl = shareUrl,
        trailerUrl = trailerUrl,
        modifier = modifier,
    )
    if (providers.isNotEmpty()) {
        WatchProvidersSection(providers = providers, link = region?.link)
    }
}
