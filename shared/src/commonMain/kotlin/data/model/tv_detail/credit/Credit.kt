package data.model.tv_detail.credit


import kotlinx.serialization.Serializable

@Serializable
data class Credit(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int = 0
)
