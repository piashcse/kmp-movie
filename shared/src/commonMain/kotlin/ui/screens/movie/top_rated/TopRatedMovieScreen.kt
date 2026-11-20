package ui.screens.movie.top_rated

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import ui.screens.base.GenreFilteredListScreen

@Composable
fun TopRatedMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TopRatedMovieViewModel = koinViewModel(),
) {
    GenreFilteredListScreen(
        viewModel = viewModel,
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        getContentDescription = { it.title },
        onNavigateToDetail = onNavigateToDetail,
    )
}
