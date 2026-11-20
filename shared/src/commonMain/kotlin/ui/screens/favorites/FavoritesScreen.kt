package ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.model.local.MediaType
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.empty_celebrities_subtitle
import kmp_movie.composeapp.generated.resources.empty_celebrities_title
import kmp_movie.composeapp.generated.resources.empty_movies_subtitle
import kmp_movie.composeapp.generated.resources.empty_movies_title
import kmp_movie.composeapp.generated.resources.empty_tv_series_subtitle
import kmp_movie.composeapp.generated.resources.empty_tv_series_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.component.MediaItemCard
import ui.theme.AppTheme

@Composable
fun FavoritesScreen(
    mediaType: MediaType,
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = koinViewModel(),
) {
    val favorites by viewModel.favorites.collectAsState()
    val isLoaded by viewModel.isLoaded.collectAsState()
    val filtered = remember(favorites, mediaType) {
        favorites.filter { it.mediaType == mediaType }
    }

    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            when {
                !isLoaded -> CircularProgressIndicator()
                filtered.isEmpty() -> EmptyFavoritesView(mediaType)
                else -> LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 180.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = filtered,
                        key = { "${it.mediaType}_${it.id}" },
                    ) { favorite ->
                        MediaItemCard(
                            id = favorite.id,
                            title = favorite.title,
                            posterPath = favorite.posterPath,
                            releaseDate = favorite.releaseDate.orEmpty(),
                            mediaType = favorite.mediaType,
                            onClick = { onNavigateToDetail(favorite.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyFavoritesView(mediaType: MediaType) {
    val title = stringResource(
        when (mediaType) {
            MediaType.MOVIE -> Res.string.empty_movies_title
            MediaType.TV -> Res.string.empty_tv_series_title
            MediaType.PERSON -> Res.string.empty_celebrities_title
        }
    )
    val subtitle = stringResource(
        when (mediaType) {
            MediaType.MOVIE -> Res.string.empty_movies_subtitle
            MediaType.TV -> Res.string.empty_tv_series_subtitle
            MediaType.PERSON -> Res.string.empty_celebrities_subtitle
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = AppTheme.colors.primary,
            modifier = Modifier.size(64.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.colors.onSurfaceVariant,
        )
    }
}
