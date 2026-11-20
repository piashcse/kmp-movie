package ui.screens.movie.now_playing

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import ui.screens.base.GenreFilteredListScreen

@Composable
fun NowPlayingScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: NowPlayingViewModel = koinViewModel(),
) {
    GenreFilteredListScreen(
        viewModel = viewModel,
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail,
    )
}
