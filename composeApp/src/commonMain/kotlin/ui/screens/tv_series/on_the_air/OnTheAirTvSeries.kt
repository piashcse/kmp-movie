package ui.screens.tv_series.on_the_air

import androidx.compose.runtime.Composable
import data.model.TvSeriesItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen

@Composable
fun OnTheAirTvSeries(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: OnTheAirTvSeriesViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadOnTheAirTvSeries() },
        getItems = { it.tvSeriesList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { (it as TvSeriesItem).posterPath },
        getItemId = { (it as TvSeriesItem).id },
        onNavigateToDetail = onNavigateToDetail
    )
}