package ui.screens.tv_series.top_rated

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import ui.screens.base.GenreFilteredListScreen

@Composable
fun TopRatedTvSeriesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TopRatedTvSeriesViewModel = koinViewModel(),
) {
    GenreFilteredListScreen(
        viewModel = viewModel,
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        getContentDescription = { it.name },
        onNavigateToDetail = onNavigateToDetail,
    )
}
