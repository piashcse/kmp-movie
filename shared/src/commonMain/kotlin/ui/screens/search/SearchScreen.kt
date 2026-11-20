package ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import ui.component.SearchBar
import ui.component.SearchFilter
import ui.component.SearchResults
import ui.screens.AppViewModel

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTvSeries: (Int) -> Unit,
    onNavigateToArtist: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = koinViewModel(),
) {
    var query by remember { mutableStateOf("") }
    var searchFilter by remember { mutableStateOf(SearchFilter.MOVIES) }
    val isLoading by viewModel.isLoading.collectAsState()
    val movieSearchData by viewModel.movieSearchData.collectAsState()
    val tvSeriesSearchData by viewModel.tvSeriesSearchData.collectAsState()
    val celebritySearchData by viewModel.celebritySearchData.collectAsState()

    fun search(term: String, filter: SearchFilter) {
        when (filter) {
            SearchFilter.MOVIES -> viewModel.movieSearch(term)
            SearchFilter.TV_SERIES -> viewModel.tvSeriesSearch(term)
            SearchFilter.CELEBRITIES -> viewModel.celebritySearch(term)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        SearchBar(
            query = query,
            onQueryChange = {
                query = it
                search(it, searchFilter)
            },
            searchFilter = searchFilter,
            isLoading = isLoading,
            onBack = onBack,
        )
        SearchResults(
            movieSearchData = movieSearchData,
            tvSeriesSearchData = tvSeriesSearchData,
            celebritySearchData = celebritySearchData,
            currentFilter = searchFilter,
            onFilterChange = { filter ->
                searchFilter = filter
                // Re-run the current term for the newly selected content type.
                search(query, filter)
            },
            query = query,
            isLoading = isLoading,
            onNavigateToMovie = onNavigateToMovie,
            onNavigateToTvSeries = onNavigateToTvSeries,
            onNavigateToCelebrity = onNavigateToArtist,
        )
    }
}
