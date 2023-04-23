package ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberAsyncImagePainter
import data.model.MovieItem
import utils.AppConstant
import utils.cornerRadius

@Composable
internal fun MovieList(listItems: List<MovieItem>, onclick: (id: Int) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
        content = {
            items(listItems) {
                Column(
                    modifier = Modifier.padding(
                        start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp
                    )
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            AppConstant.IMAGE_URL.plus(
                                it.poster_path
                            )
                        ),
                        contentDescription = it.poster_path,
                        modifier = Modifier.size(250.dp).cornerRadius(10).shimmerBackground(
                            RoundedCornerShape(5.dp)
                        ).clickable {
                            onclick(it.id)
                        },
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        })
}
