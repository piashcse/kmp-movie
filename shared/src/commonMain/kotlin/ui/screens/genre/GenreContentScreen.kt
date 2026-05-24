package ui.screens.genre

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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