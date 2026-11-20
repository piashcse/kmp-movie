package ui.screens.movie.upcoming

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import ui.screens.base.GenreFilteredListScreen

@Composable
fun UpcomingMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: UpcomingMovieViewModel = koinViewModel(),
) {
    GenreFilteredListScreen(
        viewModel = viewModel,
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        getContentDescription = { it.title },
        onNavigateToDetail = onNavigateToDetail,
    )
}
