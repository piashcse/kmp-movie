package ui.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // "All" chip to reset filter
        FilterChip(
            selected = selectedGenreId == null,
            onClick = { onGenreSelected(null) },
            label = { Text("All") },
            shape = RoundedCornerShape(50.dp) // Pill-shaped corners
        )

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

        genres.forEach { genre ->
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            FilterChip(
                selected = selectedGenreId == genre.id,
                onClick = { onGenreSelected(genre.id) },
                label = { Text(genre.name) },
                shape = RoundedCornerShape(50.dp) // Pill-shaped corners
            )
        }
    }
}