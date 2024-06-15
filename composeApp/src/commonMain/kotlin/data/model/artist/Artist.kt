package data.model.artist
import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)