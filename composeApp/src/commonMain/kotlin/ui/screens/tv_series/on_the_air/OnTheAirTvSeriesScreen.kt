package ui.screens.tv_series.on_the_air

import androidx.compose.runtime.Composable
import data.model.TvSeriesItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.screens.common.GenericUiState

@Composable
fun OnTheAirTvSeriesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: OnTheAirTvSeriesViewModel = koinViewModel()
) {
    GenericListScreen<TvSeriesItem, GenericUiState<TvSeriesItem>>(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadOnTheAirTvSeries() },
        getItems = { it.items },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}