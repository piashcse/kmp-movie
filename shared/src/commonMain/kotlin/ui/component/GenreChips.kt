package ui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.Spacing
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.all
import org.jetbrains.compose.resources.stringResource

interface GenreItem {
    val id: Int
    val name: String
}

@Composable
fun GenreChips(
    genres: List<GenreItem>,
    selectedGenreId: Int?, // null means "All" or no filter
    onGenreSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = Spacing.lg, vertical = Spacing.sm)
    ) {
        // "All" chip to reset filter
        FilterChip(
            selected = selectedGenreId == null,
            onClick = { onGenreSelected(null) },
            label = { Text(stringResource(Res.string.all)) },
            shape = CircleShape
        )

        Spacer(modifier = Modifier.width(Spacing.xs))

        genres.forEach { genre ->
            Spacer(modifier = Modifier.width(Spacing.xs))

            FilterChip(
                selected = selectedGenreId == genre.id,
                onClick = { onGenreSelected(genre.id) },
                label = { Text(genre.name) },
                shape = CircleShape
            )
        }
    }
}