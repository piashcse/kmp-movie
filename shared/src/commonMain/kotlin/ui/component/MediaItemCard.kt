package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import constant.AppConstant
import data.model.local.FavoriteItem
import data.model.local.MediaType
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ui.theme.AppTheme
import utils.cornerRadius


@Composable
fun MediaItemCard(
    id: Int,
    title: String,
    posterPath: String?,
    releaseDate: String,
    mediaType: MediaType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showActions: Boolean = true
) {
    val repository = koinInject<data.repository.Repository>()
    val scope = rememberCoroutineScope()

    var isFavorite by remember { mutableStateOf(false) }

    // Load initial states
    LaunchedEffect(id, mediaType) {
        isFavorite = repository.isFavorite(id, mediaType)
    }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surface)
    ) {
        Box {
            CoilImage(
                imageModel = { AppConstant.IMAGE_URL + (posterPath ?: "") },
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .cornerRadius(10)
                    .shimmerBackground(RoundedCornerShape(10.dp)),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                ),
                component = rememberImageComponent {
                    +CircularRevealPlugin(duration = 800)
                },
            )

            if (showActions) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FavoriteButton(
                        isFavorite = isFavorite,
                        onClick = {
                            isFavorite = !isFavorite
                            scope.launch {
                                if (isFavorite) {
                                    repository.addFavorite(
                                        FavoriteItem(
                                            id = id,
                                            mediaType = mediaType,
                                            title = title,
                                            posterPath = posterPath,
                                            releaseDate = releaseDate
                                        )
                                    )
                                } else {
                                    repository.removeFavorite(id, mediaType)
                                }
                            }
                        }
                    )


                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = AppTheme.colors.onSurface
            )
            
            if (releaseDate.isNotEmpty()) {
                Text(
                    text = releaseDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.colors.onSurfaceVariant
                )
            }
        }
    }
}