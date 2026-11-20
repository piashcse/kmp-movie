package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    val englishName: String,
    @SerialName("iso_639_1")
    val iso6391: String,
    val name: String
)
