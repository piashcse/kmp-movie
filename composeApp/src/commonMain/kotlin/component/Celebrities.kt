package component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import constant.AppConstant
import data.model.celebrities.Celebrity
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.popularity
import org.jetbrains.compose.resources.stringResource
import theme.cornerRadius
import utils.roundTo

@Composable
fun Celebrities(
    celebrities: List<Celebrity>,
    gridState: LazyGridState,
    onclick: (id: Int) -> Unit
) {
    LazyVerticalGrid(
        columns =  GridCells.Adaptive(minSize = 180.dp),
        state = gridState,
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp)
    ) {
        items(celebrities) { item ->
            Column(modifier = Modifier.padding(5.dp)) {
                CoilImage(
                    modifier = Modifier
                        .size(230.dp)
                        .cornerRadius(10)
                        .shimmerBackground(RoundedCornerShape(10.dp))
                        .clickable {
                            onclick(item.id)
                        },
                    imageModel = { AppConstant.IMAGE_URL + item.profilePath },
                    component = rememberImageComponent {
                        +CircularRevealPlugin(duration = 800)
                    },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                    ),
                )
                Column(Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${stringResource(Res.string.popularity)} ${
                            item.popularity.roundTo(
                                1
                            )
                        }",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}