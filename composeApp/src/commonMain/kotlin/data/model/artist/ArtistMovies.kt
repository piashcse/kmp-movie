package data.model.artist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistMovies(
    @SerialName("cast")
    val cast: List<ArtistMovie>,
    @SerialName("id")
    val id: Int
)