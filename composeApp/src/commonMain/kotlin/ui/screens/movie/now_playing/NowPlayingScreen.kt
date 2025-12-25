package ui.screens.movie.now_playing

import androidx.compose.runtime.Composable
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen

@Composable
fun NowPlayingScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: NowPlayingViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadNowPlayingMovies() },
        getItems = { it.movieList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}
