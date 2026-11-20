package data.model.tv_detail.credit


import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val adult: Boolean,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)
