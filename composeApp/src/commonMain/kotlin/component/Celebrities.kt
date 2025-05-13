package component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.skydoves.landscapist.coil3.CoilImage
import data.model.celebrities.Celebrity
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import theme.cornerRadius
import utils.AppConstant

@Composable
fun Celebrities(
    celebrities: List<Celebrity>,
    gridState: LazyGridState,
    onclick: (id: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = Modifier
            .padding(horizontal = 5.dp)
    ) {
        items(celebrities) { item ->
            item.let {
                Column(modifier = Modifier.padding(5.dp)) {
                    CoilImage(
                        modifier = Modifier
                            .size(230.dp)
                            .cornerRadius(10)
                            .clickable {
                                onclick(item.id)
                            },
                        imageModel = { AppConstant.IMAGE_URL + item.profilePath },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "Item"
                        ),
                    )
                    Column(Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)) {
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Popularity: ${item.popularity}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}