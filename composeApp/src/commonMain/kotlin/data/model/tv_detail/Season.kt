package data.model.tv_detail


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Season(
    @SerialName("air_date")
    val airDate: String?,
    @SerialName("episode_count")
    val episodeCount: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("season_number")
    val seasonNumber: Int,
    @SerialName("vote_average")
    val voteAverage: Double
)