package ui.screens.artist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import constant.AppConstant
import data.model.artist.ArtistMovie
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.artist_detail
import kmp_movie.composeapp.generated.resources.artist_movies
import kmp_movie.composeapp.generated.resources.biography
import kmp_movie.composeapp.generated.resources.birth_day
import kmp_movie.composeapp.generated.resources.place_of_birth
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import theme.cornerRadius
import ui.component.ExpandableText
import ui.component.base.BaseColumn
import ui.component.FavoriteButton
import org.koin.compose.koinInject
import data.model.local.FavoriteItem
import data.model.local.MediaType
import kotlinx.coroutines.launch
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun ArtistDetail(
    personId: Int,
    onNavigateToMovie: (Int) -> Unit,
    onNavigateToTvSeries: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: ArtistDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val repository = koinInject<data.repository.Repository>()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(personId) {
        viewModel.fetchArtistDetail(personId)
        isFavorite = repository.isFavorite(personId, MediaType.PERSON)
    }

    BaseColumn(
        loading = uiState.isLoading,
        errorMessage = uiState.errorMessage
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp),
        ) {
            uiState.artistDetail?.let { artist ->
                Box {
                    Row {
                        CoilImage(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .height(250.dp)
                                .width(190.dp)
                                .cornerRadius(10),
                            imageModel = { AppConstant.IMAGE_URL + artist.profilePath },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                            ),
                            component = rememberImageComponent {
                                +CircularRevealPlugin(duration = 800)
                            },
                        )
                        Column {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = artist.name,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Medium
                            )
                            PersonalInfo(
                                stringResource(Res.string.artist_detail),
                                artist.knownForDepartment
                            )
                            PersonalInfo(
                                stringResource(Res.string.artist_detail),
                                artist.gender.toString()
                            )
                            PersonalInfo(stringResource(Res.string.birth_day), artist.birthday ?: "")
                            PersonalInfo(
                                stringResource(Res.string.place_of_birth),
                                artist.placeOfBirth ?: ""
                            )
                        }
                    }

                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(top = 8.dp, start = 8.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Favorite Button
                    val scope = rememberCoroutineScope()

                    FavoriteButton(
                        isFavorite = isFavorite,
                        onClick = {
                            isFavorite = !isFavorite
                            scope.launch {
                                if (isFavorite) {
                                    repository.addFavorite(
                                        FavoriteItem(
                                            id = personId,
                                            mediaType = MediaType.PERSON,
                                            title = artist.name,
                                            posterPath = artist.profilePath,
                                            releaseDate = artist.birthday
                                        )
                                    )
                                } else {
                                    repository.removeFavorite(personId, MediaType.PERSON)
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 8.dp, end = 8.dp)
                    )
                }
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(Res.string.biography),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
                ExpandableText(text = artist.biography, maxLines = 12)
                uiState.artistMovies?.let {
                    ArtistMoviesAndTvShows(it) { item ->
                        if (item.mediaType == "movie") {
                            onNavigateToMovie(item.id)
                        } else {
                            onNavigateToTvSeries(item.id)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun PersonalInfo(title: String, info: String) {
    Column(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(text = info, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
    }
}

@Composable
fun ArtistMoviesAndTvShows(artistMovies: List<ArtistMovie>, onMovieClick: (ArtistMovie) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)) {
        if (artistMovies.isNotEmpty()) {
            Text(
                text = stringResource(Res.string.artist_movies),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(artistMovies, itemContent = { item ->
                Column(
                    modifier = Modifier.padding(
                        start = 0.dp, end = 8.dp, top = 5.dp, bottom = 5.dp
                    )
                ) {
                    CoilImage(
                        modifier = Modifier
                            .height(180.dp)
                            .width(135.dp)
                            .cornerRadius(10)
                            .clickable {
                                onMovieClick(item)
                            },
                        imageModel = { AppConstant.IMAGE_URL.plus(item.posterPath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                        ),
                        component = rememberImageComponent {
                            +CircularRevealPlugin(
                                duration = 800
                            )
                        },
                    )
                }
            })
        }
    }
}
