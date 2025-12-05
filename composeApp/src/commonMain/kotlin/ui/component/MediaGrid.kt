package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import constant.AppConstant
import utils.cornerRadius

/**
 * Generic grid component for displaying media items (movies, TV series, celebrities)
 * 
 * @param T The type of items to display
 * @param items List of items to display in the grid
 * @param gridState State for the lazy grid
 * @param getImagePath Function to extract image path from item
 * @param getItemId Function to extract ID from item
 * @param onClick Callback when an item is clicked
 * @param imageHeight Height of the image (default 250.dp)
 * @param additionalContent Optional composable to display below the image
 */
@Composable
fun <T> MediaGrid(
    items: List<T>,
    gridState: LazyGridState,
    getImagePath: (T) -> String?,
    getItemId: (T) -> Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 250.dp,
    additionalContent: @Composable (T) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 180.dp),
        state = gridState,
        modifier = modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp)
    ) {
        items(items) { item ->
            Column(
                modifier = Modifier.padding(
                    start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp
                )
            ) {
                CoilImage(
                    modifier = Modifier
                        .height(imageHeight)
                        .fillMaxWidth()
                        .cornerRadius(10)
                        .shimmerBackground(RoundedCornerShape(10.dp))
                        .clickable { onClick(getItemId(item)) },
                    imageModel = { AppConstant.IMAGE_URL + (getImagePath(item) ?: "") },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                    ),
                    component = rememberImageComponent {
                        +CircularRevealPlugin(duration = 800)
                    },
                )
                
                // Optional additional content (e.g., celebrity name and popularity)
                additionalContent(item)
            }
        }
    }
}
