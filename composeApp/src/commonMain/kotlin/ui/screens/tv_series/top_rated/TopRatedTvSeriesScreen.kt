package ui.screens.tv_series.top_rated

import androidx.compose.runtime.Composable
import data.model.TvSeriesItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen

@Composable
fun TopRatedTvSeriesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TopRatedTvSeriesViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadTopRatedTvSeries() },
        getItems = { it.tvSeriesList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { (it as TvSeriesItem).posterPath },
        getItemId = { (it as TvSeriesItem).id },
        onNavigateToDetail = onNavigateToDetail
    )
}