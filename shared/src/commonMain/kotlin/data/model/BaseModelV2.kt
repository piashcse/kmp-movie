package data.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseModelV2(
    val page: Int,
    val results: List<MovieItem>,
    val total_pages: Int,
    val total_results: Int
)