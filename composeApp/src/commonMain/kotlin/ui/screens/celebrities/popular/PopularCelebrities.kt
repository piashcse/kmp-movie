package ui.screens.celebrities.popular

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import ui.component.Celebrities
import ui.component.base.BaseColumn
import utils.OnGridPagination

@Composable
fun PopularCelebrities(
    onNavigateToDetail: (Int) -> Unit,
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
                onNavigateToDetail(celebrityId)
            }
            OnGridPagination(gridState = gridState) {
                viewModel.loadPopularCelebrities()
            }
        }
    }
}