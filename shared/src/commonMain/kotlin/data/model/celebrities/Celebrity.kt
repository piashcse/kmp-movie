package data.model.celebrities

import kotlinx.serialization.Serializable

@Serializable
data class Celebrity(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownFor: List<KnownFor>?,
    val knownForDepartment: String?,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?
)
