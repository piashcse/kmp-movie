package ui.screens.tv_series.airing_today

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ui.component.TvSeries
import ui.component.base.BaseColumn
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import utils.OnGridPagination

@Composable
fun AiringTodayTvSeries(
    navigator: Navigator,
    viewModel: AiringTodayTvSeriesViewModel = viewModel { AiringTodayTvSeriesViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.loadAiringTodayTvSeries()
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
                viewModel.loadAiringTodayTvSeries()
            }
        }
    }
}
