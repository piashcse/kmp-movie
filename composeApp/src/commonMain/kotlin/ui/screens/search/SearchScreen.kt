package ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import ui.component.SearchBar
import ui.component.SearchResults
import ui.screens.AppViewModel

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTvSeries: (Int) -> Unit,
    onNavigateToArtist: (Int) -> Unit,
    viewModel: AppViewModel = koinViewModel()
) {
    var searchFilter by remember { mutableStateOf("Movies") }
    val isLoading by viewModel.isLoading.collectAsState()
    val movieSearchData by viewModel.movieSearchData.collectAsState()
    val tvSeriesSearchData by viewModel.tvSeriesSearchData.collectAsState()
    val celebritySearchData by viewModel.celebritySearchData.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            viewModel = viewModel,
            searchFilter = searchFilter,
            isLoading = isLoading,
            pressOnBack = onBack
        )
        SearchResults(
            movieSearchData = movieSearchData,
            tvSeriesSearchData = tvSeriesSearchData,
            celebritySearchData = celebritySearchData,
            currentFilter = searchFilter,
            onFilterChange = { searchFilter = it },
            onResultClick = {},
            onNavigateToMovie = onNavigateToMovie,
            onNavigateToTvSeries = onNavigateToTvSeries,
            onNavigateToCelebrity = onNavigateToArtist
        )
    }
}
