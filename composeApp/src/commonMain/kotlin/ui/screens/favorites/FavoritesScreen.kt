package ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.model.local.FavoriteItem
import data.model.local.MediaType
import org.koin.compose.viewmodel.koinViewModel
import ui.component.MediaItemCard
import ui.theme.AppTheme
import utils.network.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favorites",
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
                    val favorites = uiState.data
                    if (favorites.isEmpty()) {
                        EmptyFavoritesView()
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 128.dp),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(favorites) { favorite ->
                                MediaItemCard(
                                    id = favorite.id,
                                    title = favorite.title,
                                    posterPath = favorite.posterPath,
                                    releaseDate = favorite.releaseDate ?: "",
                                    mediaType = favorite.mediaType,
                                    onClick = {
                                        // Navigate to detail based on media type
                                        when (favorite.mediaType) {
                                            MediaType.MOVIE -> { /* Navigate to movie detail */ }
                                            MediaType.TV -> { /* Navigate to TV series detail */ }
                                            MediaType.PERSON -> { /* Navigate to person detail */ }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Text(text = "Failed to load favorites: ${uiState.exception.message}")
                }
            }
        }
    }
}

@Composable
fun EmptyFavoritesView() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = AppTheme.colors.primary,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No favorites yet",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Start adding your favorite movies and TV shows!",
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant
        )
    }
}