package data.model.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import utils.getCurrentTimestamp

@Serializable
data class FavoriteItem(
    @SerialName("id")
    val id: Int,
    @SerialName("mediaType")
    val mediaType: MediaType, // movie, tv, person
    @SerialName("title")
    val title: String,
    @SerialName("posterPath")
    val posterPath: String?,
    @SerialName("releaseDate")
    val releaseDate: String?,
    @SerialName("addedAt")
    val addedAt: Long
) {
    constructor(
        id: Int,
        mediaType: MediaType,
        title: String,
        posterPath: String?,
        releaseDate: String?
    ) : this(id, mediaType, title, posterPath, releaseDate, currentTimeMillis())

    companion object {
        fun currentTimeMillis(): Long = getCurrentTimestamp()
    }
}

enum class MediaType {
    MOVIE, TV, PERSON
}