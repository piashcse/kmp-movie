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
import data.model.local.MediaType
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.component.MediaItemCard
import ui.theme.AppTheme
import utils.network.UiState
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.favorites

@Composable
fun FavoritesScreen(
    mediaType: MediaType,
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = koinViewModel()
) {
    LaunchedEffect(mediaType) {
        viewModel.loadFavorites(mediaType)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val uiState = viewModel.state.collectAsState().value) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UiState.Success -> {
                    val favorites = uiState.data
                    if (favorites.isEmpty()) {
                        EmptyFavoritesView(mediaType)
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 128.dp),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(favorites) { favorite ->
                                MediaItemCard(
                                    id = favorite.id,
                                    title = favorite.title,
                                    posterPath = favorite.posterPath,
                                    releaseDate = favorite.releaseDate ?: "",
                                    mediaType = favorite.mediaType,
                                    onClick = { onNavigateToDetail(favorite.id) }
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
fun EmptyFavoritesView(mediaType: MediaType) {
    val title = when (mediaType) {
        MediaType.MOVIE -> "No favorite movies yet"
        MediaType.TV -> "No favorite TV series yet"
        MediaType.PERSON -> "No favorite celebrities yet"
    }

    val subtitle = when (mediaType) {
        MediaType.MOVIE -> "Start adding your favorite movies!"
        MediaType.TV -> "Start adding your favorite TV series!"
        MediaType.PERSON -> "Start adding your favorite celebrities!"
    }

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
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant
        )
    }
}