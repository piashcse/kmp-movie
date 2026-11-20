package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import constant.AppConstant
import theme.cornerRadius

@Composable
fun <T> RecommendedSection(
    title: String,
    items: List<T>,
    getImagePath: (T) -> String?,
    getItemId: (T) -> Int,
    onNavigateToDetail: (Int) -> Unit,
) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(items, key = { getItemId(it) }) { item ->
                Column(
                    modifier = Modifier.padding(start = 0.dp, end = 8.dp, top = 5.dp, bottom = 5.dp),
                ) {
                    ImageLoad(
                        url = AppConstant.imageUrl(getImagePath(item)),
                        modifier = Modifier
                            .size(135.dp, 180.dp)
                            .cornerRadius(10)
                            .clickable { onNavigateToDetail(getItemId(item)) },
                    )
                }
            }
        }
    }
}
