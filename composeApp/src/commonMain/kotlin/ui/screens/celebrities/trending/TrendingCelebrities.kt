package ui.screens.celebrities.trending

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ui.component.Celebrities
import ui.component.base.BaseColumn
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import utils.OnGridPagination

@Composable
fun TrendingCelebrities(
    navigator: Navigator,
    viewModel: TrendingCelebritiesViewModel = viewModel { TrendingCelebritiesViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.loadTrendingCelebrities()
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        uiState.celebrityList?.let {
            Celebrities(it, gridState) { celebrityId ->
                navigator.navigate(
                    NavigationScreen.ArtistDetail.route.plus(
                        "/${celebrityId}"
                    )
                )
            }
            OnGridPagination(gridState = gridState) {
                viewModel.loadTrendingCelebrities()
            }
        }
    }
}