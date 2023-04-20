package data.model.moviedetail

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int?,
    val name: String
)