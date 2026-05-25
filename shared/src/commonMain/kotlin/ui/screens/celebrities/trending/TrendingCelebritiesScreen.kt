package ui.screens.celebrities.trending

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.popularity
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.component.GenericListScreen
import utils.roundTo

@Composable
fun TrendingCelebritiesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: TrendingCelebritiesViewModel = koinViewModel()
) {
    GenericListScreen(
        uiState = viewModel.uiState,
        loadItems = { viewModel.loadTrendingCelebrities() },
        getItems = { it.celebrityList },
        getIsLoading = { it.isLoading },
        getErrorMessage = { it.errorMessage },
        getImagePath = { it.profilePath },
        getItemId = { it.id },
        onNavigateToDetail = onNavigateToDetail,
        imageHeight = 230.dp,
        additionalContent = { celebrity ->
            Column(Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)) {
                Text(
                    text = celebrity.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "${stringResource(Res.string.popularity)} ${celebrity.popularity.roundTo(1)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    )
}