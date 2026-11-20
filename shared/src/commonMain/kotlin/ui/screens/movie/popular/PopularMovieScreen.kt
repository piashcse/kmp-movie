package ui.screens.movie.popular

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import ui.screens.base.GenreFilteredListScreen

@Composable
fun PopularMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PopularMovieViewModel = koinViewModel(),
) {
    GenreFilteredListScreen(
        viewModel = viewModel,
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail,
    )
}
