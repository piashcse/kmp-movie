package data.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseModel<T>(
    val dates: Dates? = null,

    val page: Int = 1,

    val results: List<T> = emptyList(),

    val totalPages: Int = 0,

    val totalResults: Int = 0
)

@Serializable
data class Dates(
    val maximum: String,

    val minimum: String
)
