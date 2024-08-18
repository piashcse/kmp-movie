package data.model.movie_detail

import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)