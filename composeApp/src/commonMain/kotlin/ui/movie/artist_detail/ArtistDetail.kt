package ui.movie.artist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.artist_detail
import kmp_movie.composeapp.generated.resources.biography
import kmp_movie.composeapp.generated.resources.birth_day
import kmp_movie.composeapp.generated.resources.place_of_birth
import org.jetbrains.compose.resources.stringResource
import theme.DefaultBackgroundColor
import theme.FontColor
import theme.SecondaryFontColor
import theme.cornerRadius
import ui.component.ProgressIndicator
import utils.AppConstant

@Composable
fun ArtistDetail(
    personId: Int,
    viewModel: ArtistDetailViewModel = viewModel { ArtistDetailViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(personId) {
        viewModel.fetchArtistDetail(personId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(DefaultBackgroundColor)
            .padding(8.dp)
    ) {
        if (uiState.isLoading) {
            ProgressIndicator()
        }

        uiState.artistDetail?.let { artist ->
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
                        contentDescription = "artist image"
                    ),
                    component = rememberImageComponent {
                        +CircularRevealPlugin(duration = 800)
                    },
                )
                Column {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = artist.name ?: "",
                        color = FontColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium
                    )
                    PersonalInfo(
                        stringResource(Res.string.artist_detail),
                        artist.knownForDepartment
                    )
                    PersonalInfo(stringResource(Res.string.artist_detail), artist.gender.toString())
                    PersonalInfo(stringResource(Res.string.birth_day), artist.birthday ?: "")
                    PersonalInfo(
                        stringResource(Res.string.place_of_birth),
                        artist.placeOfBirth ?: ""
                    )
                }
            }
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(Res.string.biography),
                color = SecondaryFontColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
            Text(text = artist.biography ?: "")
        }
    }
}

@Composable
fun PersonalInfo(title: String, info: String) {
    Column(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)) {
        Text(
            text = title,
            color = SecondaryFontColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(text = info, color = FontColor, fontSize = 16.sp)
    }
}