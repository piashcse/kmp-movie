package ui.screens.movie.now_playing

import androidx.compose.runtime.Composable
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.screens.common.GenericUiState

@Composable
fun NowPlayingScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: NowPlayingViewModel = koinViewModel()
) {
    GenericListScreen<MovieItem, GenericUiState<MovieItem>>(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadNowPlayingMovies() },
        getItems = { it.items },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}
