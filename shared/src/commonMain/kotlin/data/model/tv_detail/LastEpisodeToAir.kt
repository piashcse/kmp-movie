package data.model.tv_detail


import kotlinx.serialization.Serializable

@Serializable
data class LastEpisodeToAir(
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val id: Int,
    val name: String,
    val overview: String,
    val productionCode: String,
    val runtime: Double?,
    val seasonNumber: Int,
    val showId: Int,
    val stillPath: String?,
    val voteAverage: Double,
    val voteCount: Int
)
