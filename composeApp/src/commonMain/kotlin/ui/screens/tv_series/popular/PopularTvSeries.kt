package ui.screens.tv_series.popular

import androidx.compose.runtime.Composable
import data.model.TvSeriesItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen

@Composable
fun PopularTvSeries(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PopularTvSeriesViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadPopularTvSeries() },
        getItems = { it.tvSeriesList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { (it as TvSeriesItem).posterPath },
        getItemId = { (it as TvSeriesItem).id },
        onNavigateToDetail = onNavigateToDetail
    )
}