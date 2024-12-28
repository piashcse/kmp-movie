package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseModelV2(
    @SerialName("page")
    val page: Int,

    @SerialName("results")
    val results: List<MovieItem>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_results")
    val totalResults: Int
)