package ui.screens.genre

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreContentScreen(
    genreId: Int,
    genreName: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GenreContentViewModel = koinViewModel()
) {
    LaunchedEffect(genreId) {
        viewModel.setGenre(genreId, genreName)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = genreName,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.colors.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
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
            GenericListScreen(
                uiState = viewModel.uiState,
                loadItems = { viewModel.loadMoviesByGenre() },
                getItems = { it.movieList },
                getIsLoading = { it.isLoading },
                getErrorMessage = { it.errorMessage },
                getImagePath = { it.posterPath },
                getItemId = { it.id },
                onNavigateToDetail = { /* Handle navigation to movie detail */ }
            )
        }
    }
}