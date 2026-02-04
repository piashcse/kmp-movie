package data.model.genre

import data.model.movie_detail.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("genres")
    val genres: List<Genre>
)
