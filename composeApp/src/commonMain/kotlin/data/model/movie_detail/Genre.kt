package data.model.movie_detail

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int?,
    val name: String
)