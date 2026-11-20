package data.model.tv_detail


import kotlinx.serialization.Serializable

@Serializable
data class CreatedBy(
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val originalName: String,
    val profilePath: String?
)
