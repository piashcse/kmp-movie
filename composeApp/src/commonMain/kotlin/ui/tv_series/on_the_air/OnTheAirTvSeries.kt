package ui.tv_series.on_the_air

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
import ui.component.TvItemList

@Composable
fun OnTheAirTvSeries(
    navigator: Navigator,
    viewModel: OnTheAirTvSeriesViewModel = viewModel { OnTheAirTvSeriesViewModel() }
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val tvItems by viewModel.onTheAirTvSeriesResponse.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onTheAirTvSeries(1)
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TvItemList(tvItems) { seriesId ->
            navigator.navigate(NavigationScreen.TvSeriesDetail.route.plus("/$seriesId"))
        }
        if (isLoading) {
            ProgressIndicator()
        }
    }
}
