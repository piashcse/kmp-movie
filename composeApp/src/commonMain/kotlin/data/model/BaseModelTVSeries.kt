package data.model


import kotlinx.serialization.Serializable

@Serializable
data class BaseModelTVSeries(
    val page: Int,
    val results: List<TvSeriesItem>,
    val total_pages: Int,
    val total_results: Int
)