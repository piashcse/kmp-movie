package ui.artistdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import data.model.artist.ArtistDetail
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
import utils.network.UiState

@Composable
fun ArtistDetail(
    personId: Int,
    viewModel: ArtistDetailViewModel = viewModel { ArtistDetailViewModel() }
) {
    var artistDetailData by remember { mutableStateOf<ArtistDetail?>(null) }
    var progressBar by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        viewModel.artistDetail(personId)
    }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(
            DefaultBackgroundColor
        ).padding(start = 8.dp, top = 8.dp, end = 8.dp)
    ) {
        if (progressBar) {
            ProgressIndicator()
        }
        artistDetailData?.let {
            Row {
                CoilImage(
                    modifier = Modifier.padding(bottom = 8.dp).height(250.dp).width(190.dp)
                        .cornerRadius(10),
                    imageModel = {
                        AppConstant.IMAGE_URL.plus(it.profile_path)
                    },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        contentDescription = "artist image",
                        colorFilter = null,
                    ),
                    component = rememberImageComponent {
                        +CircularRevealPlugin(
                            duration = 800
                        )
                    },
                )
                Column {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = artistDetailData?.name ?: "",
                        color = FontColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium
                    )
                    PersonalInfo(
                        stringResource(Res.string.artist_detail),
                        it.known_for_department
                    )
                    PersonalInfo(
                        stringResource(Res.string.artist_detail),
                        it.gender.toString()
                    )
                    PersonalInfo(
                        stringResource(Res.string.birth_day), it.birthday ?: ""
                    )
                    PersonalInfo(
                        stringResource(Res.string.place_of_birth),
                        it.place_of_birth ?: ""
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
            Text(
                text = it.biography
            )
        }
    }


    viewModel.nowPlayingResponse.collectAsState().value.let {
        when (it) {
            is UiState.Loading -> {
                progressBar = true
            }

            is UiState.Success -> {
                artistDetailData = it.data
                progressBar = false
            }

            is UiState.Error -> {
                progressBar = false
            }
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
        Text(
            text = info, color = FontColor, fontSize = 16.sp
        )
    }
}