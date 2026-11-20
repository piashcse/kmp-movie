package data.model.genre

import data.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres: List<Genre>
)
