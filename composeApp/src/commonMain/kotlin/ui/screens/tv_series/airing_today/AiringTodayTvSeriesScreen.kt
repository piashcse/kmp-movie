package ui.screens.tv_series.airing_today

import androidx.compose.runtime.Composable
import data.model.TvSeriesItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.screens.common.GenericUiState

@Composable
fun AiringTodayTvSeriesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: AiringTodayTvSeriesViewModel = koinViewModel()
) {
    GenericListScreen<TvSeriesItem, GenericUiState<TvSeriesItem>>(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadAiringTodayTvSeries() },
        getItems = { it.items },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}
