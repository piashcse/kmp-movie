package ui.screens.artist_detail

import androidx.compose.runtime.Immutable
import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovie

@Immutable
data class ArtistDetailUiState(
    val artistDetail: ArtistDetail? = null,
    val artistMovies: List<ArtistMovie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
