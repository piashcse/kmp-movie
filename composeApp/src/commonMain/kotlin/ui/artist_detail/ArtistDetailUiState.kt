package ui.artist_detail

import data.model.artist.ArtistDetail
import data.model.artist.ArtistMovie

data class ArtistDetailUiState(
    val artistDetail: ArtistDetail? = null,
    val artistMovies: List<ArtistMovie>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)