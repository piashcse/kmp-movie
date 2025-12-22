package ui.screens.movie.top_rated

import androidx.compose.runtime.Composable
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.screens.common.GenericUiState

@Composable
fun TopRatedMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TopRatedMovieViewModel = koinViewModel()
) {
    GenericListScreen<MovieItem, GenericUiState<MovieItem>>(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadTopRatedMovies() },
        getItems = { it.items },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}