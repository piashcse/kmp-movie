package ui.tv_series.popular

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.ProgressIndicator
import ui.component.TvSeries

@Composable
fun PopularTvSeries(
    navigator: Navigator,
    viewModel: PopularTvSeriesViewModel = viewModel { PopularTvSeriesViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPopularTvSeries(1)
    }


    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        uiState.tvSeriesList?.let {
            TvSeries(it) { seriesId ->
                navigator.navigate(NavigationScreen.TvSeriesDetail.route.plus("/$seriesId"))
            }
        }
        if (uiState.isLoading) {
            ProgressIndicator()
        }
    }
}
