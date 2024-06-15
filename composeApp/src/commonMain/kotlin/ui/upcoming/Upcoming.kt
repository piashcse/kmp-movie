package ui.upcoming

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import data.model.MovieItem
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.MovieList
import ui.component.ProgressIndicator
import utils.network.DataState

@Composable
fun Upcoming(
    navigator: Navigator,
    upcomingViewModel: UpcomingViewModel = viewModel { UpcomingViewModel() }
) {
    val isLoading = remember { mutableStateOf(false) }
    val movies = remember { mutableStateListOf<MovieItem>() }

    LaunchedEffect(Unit) {
        upcomingViewModel.upComing(1)
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        MovieList(movies) { movieId ->
            navigator.navigate(NavigationScreen.MovieDetail.route.plus("/$movieId"))
        }
        if (isLoading.value) {
            ProgressIndicator()
        }
    }
    upcomingViewModel.upComingMovieResponse.collectAsState().value.let {
        when (it) {
            is DataState.Loading -> {
                isLoading.value = true
            }

            is DataState.Success<List<MovieItem>> -> {
                movies.clear()
                movies.addAll(it.data)
                isLoading.value = false
            }

            is DataState.Error -> {
                isLoading.value = false
            }
        }
    }
}