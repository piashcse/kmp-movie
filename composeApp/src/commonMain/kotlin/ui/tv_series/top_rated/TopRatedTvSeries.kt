package ui.tv_series.top_rated

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import component.TvSeries
import component.base.BaseColumn
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import utils.OnGridPagination

@Composable
fun TopRatedTvSeries(
    navigator: Navigator,
    viewModel: TopRatedTvSeriesViewModel = viewModel { TopRatedTvSeriesViewModel() }
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
                navigator.navigate(NavigationScreen.TvSeriesDetail.route + "/$seriesId")
            }
            OnGridPagination(gridState = gridState) {
                viewModel.loadTopRatedTvSeries()
            }
        }
    }
}