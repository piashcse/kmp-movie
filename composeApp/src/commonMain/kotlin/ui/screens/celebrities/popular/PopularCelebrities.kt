package ui.screens.celebrities.popular

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import ui.component.Celebrities
import ui.component.base.BaseColumn
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import utils.OnGridPagination

@Composable
fun PopularCelebrities(
    navigator: Navigator,
    viewModel: PopularCelebritiesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.loadPopularCelebrities()
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
                viewModel.loadPopularCelebrities()
            }
        }
    }
}