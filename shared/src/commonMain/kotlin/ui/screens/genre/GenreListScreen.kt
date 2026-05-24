package ui.screens.genre

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.model.movie_detail.Genre
import org.koin.compose.viewmodel.koinViewModel
import ui.theme.AppTheme
import utils.network.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreListScreen(
    onNavigateToGenre: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenreListViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadGenres()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Genres",
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.colors.onSurface
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colors.surface
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val uiState = viewModel.state.value) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UiState.Success -> {
                    val genres = uiState.data
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(genres) { genre ->
                            GenreCard(
                                genre = genre,
                                onClick = {
                                    onNavigateToGenre(genre.id, genre.name)
                                }
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    Text(text = "Failed to load genres: ${uiState.exception.message}")
                }
            }
        }
    }
}

@Composable
fun GenreCard(
    genre: Genre,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Category,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = AppTheme.colors.primary
            )
            Text(
                text = genre.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = AppTheme.colors.onSurface
            )
        }
    }
}