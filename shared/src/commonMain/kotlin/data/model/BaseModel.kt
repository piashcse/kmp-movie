package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseModel<T>(
    @SerialName("dates")
    val dates: Dates?,

    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<T>,

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