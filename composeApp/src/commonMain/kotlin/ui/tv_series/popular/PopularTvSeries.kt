package ui.tv_series.popular

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import component.TvSeries
import component.base.BaseColumn
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen

@Composable
fun PopularTvSeries(
    navigator: Navigator,
    viewModel: PopularTvSeriesViewModel = viewModel { PopularTvSeriesViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPopularTvSeries(1)
    }

    BaseColumn(loading = uiState.isLoading, errorMessage = uiState.errorMessage) {
        uiState.tvSeriesList?.let {
            TvSeries(it) { seriesId ->
                navigator.navigate(NavigationScreen.TvSeriesDetail.route.plus("/$seriesId"))
            }
        }
    }
}
