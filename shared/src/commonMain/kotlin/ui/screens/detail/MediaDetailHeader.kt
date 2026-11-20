package ui.screens.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.local.FavoriteItem
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.action_back
import org.jetbrains.compose.resources.stringResource
import theme.cornerRadius
import ui.component.ExpandableText
import ui.component.FavoriteItemButton
import ui.component.ImageLoad
import ui.component.text.SubtitlePrimary
import ui.component.text.SubtitleSecondary

@Immutable
data class DetailInfo(val label: String, val value: String)

/**
 * Shared header used by the movie and TV detail screens: blurred backdrop, poster, title,
 * a 2x2 info grid, favorite toggle and the expandable overview.
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
                .height(270.dp)
                .graphicsLayer {
                    alpha = 0.8f
                    shadowElevation = 10f
                    renderEffect = BlurEffect(15f, 15f)
                },
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp),
        ) {
            FavoriteItemButton(item = favoriteItem, modifier = Modifier.size(32.dp))
        }

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .align(Alignment.TopStart),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.action_back),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }

        Column(modifier = Modifier.padding(start = 10.dp, top = 200.dp, end = 10.dp)) {
            Row {
                ImageLoad(
                    url = posterUrl,
                    modifier = Modifier
                        .size(135.dp, 180.dp)
                        .cornerRadius(10)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp)),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
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
                modifier = Modifier.padding(top = 8.dp),
                text = descriptionLabel,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
            )
            ExpandableText(text = overview, maxLines = 2)
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
