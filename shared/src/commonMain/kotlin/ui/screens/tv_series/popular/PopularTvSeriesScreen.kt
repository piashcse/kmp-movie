package ui.screens.tv_series.popular

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import ui.screens.base.GenreFilteredListScreen

@Composable
fun PopularTvSeriesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PopularTvSeriesViewModel = koinViewModel(),
) {
    GenreFilteredListScreen(
        viewModel = viewModel,
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        getContentDescription = { it.name },
        onNavigateToDetail = onNavigateToDetail,
    )
}
