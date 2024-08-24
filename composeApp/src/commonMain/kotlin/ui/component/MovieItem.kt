package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import data.model.MovieItem
import utils.AppConstant
import utils.cornerRadius

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
internal fun MovieList(listItems: List<MovieItem>, onclick: (id: Int) -> Unit) {
    val windowSizeClass = calculateWindowSizeClass()
    val rowSize = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium -> 3
        else -> 4
    }

    LazyVerticalGrid(columns = GridCells.Fixed(rowSize),
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
        content = {
            items(listItems) {
                Column(
                    modifier = Modifier.padding(
                        start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp
                    )
                ) {
                    CoilImage(
                        imageModel = {
                            AppConstant.IMAGE_URL.plus(
                                it.poster_path
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
                        modifier = Modifier.height(250.dp).fillMaxWidth().cornerRadius(10)
                            .shimmerBackground(
                                RoundedCornerShape(5.dp)
                            ).clickable {
                                onclick(it.id)
                            },
                    )
                }
            }
        })
}
