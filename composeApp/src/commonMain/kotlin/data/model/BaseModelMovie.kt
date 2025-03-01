package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseModelMovie(
    @SerialName("dates")
    val dates: Dates?,

    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<MovieItem>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class Dates(
    @SerialName("maximum")
    val maximum: String,

    @SerialName("minimum")
    val minimum: String
)