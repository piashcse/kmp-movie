package ui.screens.movie.popular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.model.MovieItem
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.component.GenreChips

@Composable
fun PopularMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PopularMovieViewModel = koinViewModel()
) {
    val availableGenres by viewModel.availableGenres.collectAsState()
    val selectedGenreId by viewModel.selectedGenreId.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GenreChips(
            genres = availableGenres,
            selectedGenreId = selectedGenreId,
            onGenreSelected = { genreId -> viewModel.selectGenre(genreId) }
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            GenericListScreen(
                uiState = viewModel.uiState,
                loadItems = { viewModel.loadPopularMovies() },
                getItems = { it.movieList },
                getIsLoading = { it.isLoading },
                getErrorMessage = { it.errorMessage },
                getImagePath = { it.posterPath },
                getItemId = { it.id },
                onNavigateToDetail = onNavigateToDetail
            )
        }
    }
}