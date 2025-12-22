package ui.screens.movie.upcoming

import androidx.compose.runtime.Composable
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.screens.common.GenericUiState

@Composable
fun UpcomingMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: UpcomingMovieViewModel = koinViewModel()
) {
    GenericListScreen<MovieItem, GenericUiState<MovieItem>>(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadUpcomingMovies() },
        getItems = { it.items },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}