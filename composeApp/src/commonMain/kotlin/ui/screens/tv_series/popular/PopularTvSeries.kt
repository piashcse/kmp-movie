package ui.screens.tv_series.popular

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
fun PopularTvSeries(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PopularTvSeriesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit){
        viewModel.loadPopularTvSeries()
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
                viewModel.loadPopularTvSeries()
            }
        }
    }
}