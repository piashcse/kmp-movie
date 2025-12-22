package ui.screens.movie.popular

import androidx.compose.runtime.Composable
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.screens.common.GenericUiState

@Composable
fun PopularMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PopularMovieViewModel = koinViewModel()
) {
    GenericListScreen<MovieItem, GenericUiState<MovieItem>>(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadPopularMovies() },
        getItems = { it.items },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}