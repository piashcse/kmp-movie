package ui.celebrities.popular

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import component.Celebrities
import component.base.BaseColumn
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun PopularCelebrities(
    navigator: Navigator,
    viewModel: PopularCelebritiesViewModel = viewModel { PopularCelebritiesViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPopularCelebrities(1)
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        uiState.celebrityList?.let {
            Celebrities(it, navigator)
        }
    }
}