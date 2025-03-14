package ui.tv_series.airing_today

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.TvSeries
import ui.component.base.BaseColumn

@Composable
fun AiringTodayTvSeries(
    navigator: Navigator,
    viewModel: AiringTodayTvSeriesViewModel = viewModel { AiringTodayTvSeriesViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAiringTodayTvSeries(1)
    }
    BaseColumn(
        loading = uiState.isLoading, errorMessage = uiState.errorMessage, onDismissError = {}) {
        uiState.tvSeriesList?.let {
            TvSeries(it) { seriesId ->
                navigator.navigate(NavigationScreen.TvSeriesDetail.route.plus("/$seriesId"))
            }
        }
    }
}

