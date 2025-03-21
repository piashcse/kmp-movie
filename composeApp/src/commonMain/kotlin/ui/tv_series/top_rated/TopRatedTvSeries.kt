package ui.tv_series.top_rated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import component.TvSeries
import component.base.BaseColumn
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen

@Composable
fun TopRatedTvSeries(
    navigator: Navigator,
    viewModel: TopRatedTvSeriesViewModel = androidx.lifecycle.viewmodel.compose.viewModel { TopRatedTvSeriesViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTopRatedTvSeries(1)
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        uiState.tvSeriesList?.let {
            TvSeries(it) { seriesId ->
                navigator.navigate(NavigationScreen.TvSeriesDetail.route.plus("/$seriesId"))
            }
        }
    }
}