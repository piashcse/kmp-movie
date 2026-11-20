package data.model.artist

import kotlinx.serialization.Serializable

@Serializable
data class ArtistMovies(
    val cast: List<ArtistMovie>,
    val id: Int = 0
)
