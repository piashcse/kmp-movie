package ui.screens.movie.top_rated

import androidx.compose.runtime.Composable
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen

@Composable
fun TopRatedMovie(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TopRatedViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadTopRatedMovies() },
        getItems = { it.movieList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { (it as MovieItem).posterPath },
        getItemId = { (it as MovieItem).id },
        onNavigateToDetail = onNavigateToDetail
    )
}