package ui.screens.movie.upcoming

import androidx.compose.runtime.Composable
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen

@Composable
fun UpcomingMovie(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: UpcomingMovieViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadUpcomingMovies() },
        getItems = { it.movieList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { (it as MovieItem).posterPath },
        getItemId = { (it as MovieItem).id },
        onNavigateToDetail = onNavigateToDetail
    )
}