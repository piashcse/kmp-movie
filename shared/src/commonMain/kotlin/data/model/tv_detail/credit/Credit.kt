package data.model.tv_detail.credit


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credit(
    @SerialName("cast")
    val cast: List<Cast>,
    @SerialName("crew")
    val crew: List<Crew>,
    @SerialName("id")
    val id: Int
)