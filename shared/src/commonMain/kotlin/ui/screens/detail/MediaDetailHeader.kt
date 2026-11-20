package ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import data.model.local.FavoriteItem
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.action_back
import org.jetbrains.compose.resources.stringResource
import theme.Spacing
import ui.component.ExpandableText
import ui.component.FavoriteItemButton
import ui.component.ImageLoad
import ui.component.text.SubtitlePrimary
import ui.component.text.SubtitleSecondary

@Immutable
data class DetailInfo(val label: String, val value: String)

/**
 * Shared header used by the movie and TV detail screens. The content flows below the
 * backdrop instead of using a fixed overlay height, so it stays readable with large fonts.
 */
@Composable
fun MediaDetailHeader(
    title: String,
    backdropUrl: String,
    posterUrl: String,
    info: List<DetailInfo>,
    descriptionLabel: String,
    overview: String,
    favoriteItem: FavoriteItem,
    onBack: () -> Unit,
) {
    Box {
        ImageLoad(
            url = backdropUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .graphicsLayer {
                    alpha = 0.8f
                    shadowElevation = 10f
                    renderEffect = BlurEffect(15f, 15f)
                },
        )

        // Scrim so titles/info stay readable over bright (especially light-theme) backdrops.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.3f to MaterialTheme.colorScheme.surface.copy(alpha = 0.45f),
                        0.6f to MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                        0.8f to MaterialTheme.colorScheme.surface,
                        1f to MaterialTheme.colorScheme.surface,
                    )
                ),
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = Spacing.md, end = Spacing.md)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.75f), CircleShape),
        ) {
            FavoriteItemButton(item = favoriteItem)
        }

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(top = Spacing.md, start = Spacing.md)
                .align(Alignment.TopStart)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.75f), CircleShape),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.action_back),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Spacing.md, end = Spacing.md, top = 110.dp, bottom = Spacing.md),
        ) {
            Row {
                ImageLoad(
                    url = posterUrl,
                    modifier = Modifier
                        .size(110.dp, 150.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium),
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = Spacing.md),
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.semantics { heading() },
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        InfoCell(info[0], Modifier.weight(1f))
                        InfoCell(info[1], Modifier.weight(1f))
                    }
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        InfoCell(info[2], Modifier.weight(1f))
                        InfoCell(info[3], Modifier.weight(1f))
                    }
                }
            }

            Text(
                modifier = Modifier.padding(top = Spacing.lg),
                text = descriptionLabel,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            ExpandableText(text = overview, maxLines = 3)
        }
    }
}

@Composable
private fun InfoCell(info: DetailInfo, modifier: Modifier = Modifier) {
    Column(modifier) {
        SubtitlePrimary(text = info.label)
        SubtitleSecondary(text = info.value)
    }
}
