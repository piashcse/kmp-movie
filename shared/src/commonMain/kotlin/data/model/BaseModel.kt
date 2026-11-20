package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseModel<T>(
    @SerialName("dates")
    val dates: Dates? = null,

    @SerialName("page")
    val page: Int = 1,

    @SerialName("results")
    val results: List<T> = emptyList(),

    @SerialName("total_pages")
    val totalPages: Int = 0,

    @SerialName("total_results")
    val totalResults: Int = 0
)

@Serializable
data class Dates(
    @SerialName("maximum")
    val maximum: String,

    @SerialName("minimum")
    val minimum: String
)