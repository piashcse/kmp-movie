package ui.screens.tv_series.airing_today

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import ui.screens.base.GenreFilteredListScreen

@Composable
fun AiringTodayTvSeriesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: AiringTodayTvSeriesViewModel = koinViewModel(),
) {
    GenreFilteredListScreen(
        viewModel = viewModel,
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail,
    )
}
