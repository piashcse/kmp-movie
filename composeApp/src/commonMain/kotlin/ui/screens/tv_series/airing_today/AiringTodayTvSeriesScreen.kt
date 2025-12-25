package ui.screens.tv_series.airing_today

import androidx.compose.runtime.Composable
import data.model.TvSeriesItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen

@Composable
fun AiringTodayTvSeriesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: AiringTodayTvSeriesViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadAiringTodayTvSeries() },
        getItems = { it.tvSeriesList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}
