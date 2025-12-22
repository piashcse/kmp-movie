package ui.screens.tv_series.popular

import androidx.compose.runtime.Composable
import data.model.TvSeriesItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.screens.common.GenericUiState

@Composable
fun PopularTvSeriesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PopularTvSeriesViewModel = koinViewModel()
) {
    GenericListScreen<TvSeriesItem, GenericUiState<TvSeriesItem>>(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadPopularTvSeries() },
        getItems = { it.items },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.posterPath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail
    )
}