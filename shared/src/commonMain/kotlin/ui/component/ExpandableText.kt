package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.show_less
import kmp_movie.composeapp.generated.resources.show_more
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExpandableText(
    text: String,
    maxLines: Int = 3,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isOverflowing) { isExpanded = !isExpanded },
    ) {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { layout ->
                if (!isExpanded) {
                    isOverflowing = layout.hasVisualOverflow
                }
            },
        )

        if (isOverflowing) {
            Text(
                text = stringResource(if (isExpanded) Res.string.show_less else Res.string.show_more),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}
