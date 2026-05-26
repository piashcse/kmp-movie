package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.cast
import org.jetbrains.compose.resources.stringResource
import theme.cornerRadius
import ui.component.text.SubtitleSecondary

@Composable
fun ImageLoad(url: String, modifier: Modifier = Modifier) {
    CoilImage(
        imageModel = { url },
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        ),
        component = rememberImageComponent {
            +CircularRevealPlugin(duration = 800)
        },
        modifier = modifier.shimmerBackground(RoundedCornerShape(5.dp)),
    )
}

data class CastMember(val id: Int, val name: String, val profilePath: String?)

@Composable
fun ArtistAndCrewSection(
    cast: List<CastMember>,
    onNavigateToArtist: (Int) -> Unit,
    imageBaseUrl: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 10.dp)) {
        Text(
            text = stringResource(Res.string.cast),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(cast) { item ->
                Column(
                    modifier = Modifier
                        .padding(end = 10.dp, bottom = 4.dp)
                        .width(80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoilImage(
                        imageModel = { imageBaseUrl + item.profilePath },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(duration = 800)
                        },
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .size(80.dp)
                            .cornerRadius(40)
                            .clickable { onNavigateToArtist(item.id) }
                    )
                    SubtitleSecondary(text = item.name)
                }
            }
        }
    }
}
