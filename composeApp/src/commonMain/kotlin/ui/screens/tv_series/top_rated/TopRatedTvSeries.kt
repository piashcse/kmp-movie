package ui.screens.tv_series.top_rated

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import ui.component.TvSeries
import ui.component.base.BaseColumn
import utils.OnGridPagination

@Composable
fun TopRatedTvSeries(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TopRatedTvSeriesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.loadTopRatedTvSeries()
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        uiState.tvSeriesList?.let {
            TvSeries(it, gridState) { seriesId ->
                onNavigateToDetail(seriesId)
            }
            OnGridPagination(gridState = gridState) {
                viewModel.loadTopRatedTvSeries()
            }
        }
    }
}