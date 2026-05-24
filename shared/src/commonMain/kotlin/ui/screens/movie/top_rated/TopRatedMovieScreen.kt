package ui.screens.movie.top_rated

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import ui.component.GenreChips

@Composable
fun TopRatedMovieScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TopRatedMovieViewModel = koinViewModel()
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
                loadItems = { viewModel.loadTopRatedMovies() },
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