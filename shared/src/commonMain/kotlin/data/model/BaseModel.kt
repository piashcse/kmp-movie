package data.model


import kotlinx.serialization.Serializable

@Serializable
data class BaseModel(
    val dates: Dates,
    val page: Int,
    val results: List<MovieItem>,
    val total_pages: Int,
    val total_results: Int
)
@Serializable
data class Dates(
    val maximum: String,
    val minimum: String
)