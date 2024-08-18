package ui.tv_series.airing_today

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
fun AiringTodayTvSeries(
    navigator: Navigator,
    viewModel: AiringTodayTvSeriesViewModel = viewModel { AiringTodayTvSeriesViewModel() }
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val tvItems by viewModel.airingTodayTvSeriesResponse.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.airingTodayTvSeries(1)
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

