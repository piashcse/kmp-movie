package data.model.tv_detail


import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)
