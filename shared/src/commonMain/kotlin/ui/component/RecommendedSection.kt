package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import constant.AppConstant
import theme.Spacing

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
            modifier = Modifier.semantics { heading() },
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(items, key = { getItemId(it) }) { item ->
                Column(
                    modifier = Modifier.padding(end = Spacing.sm, top = Spacing.xs, bottom = Spacing.xs),
                ) {
                    ImageLoad(
                        url = AppConstant.imageUrl(getImagePath(item)),
                        modifier = Modifier
                            .size(135.dp, 180.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { onNavigateToDetail(getItemId(item)) },
                    )
                }
            }
        }
    }
}
