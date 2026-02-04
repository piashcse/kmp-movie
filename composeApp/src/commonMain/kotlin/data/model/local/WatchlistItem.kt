package data.model.local

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import utils.getCurrentTimestamp

@Serializable
data class WatchlistItem(
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
    val addedAt: Long,
    @SerialName("status")
    val status: WatchlistStatus
) {
    constructor(
        id: Int,
        mediaType: MediaType,
        title: String,
        posterPath: String?,
        releaseDate: String?,
        status: WatchlistStatus = WatchlistStatus.PLAN_TO_WATCH
    ) : this(id, mediaType, title, posterPath, releaseDate, currentTimeMillis(), status)

    companion object {
        fun currentTimeMillis(): Long = getCurrentTimestamp()
    }
}

enum class WatchlistStatus {
    PLAN_TO_WATCH, WATCHING, COMPLETED, ON_HOLD, DROPPED
}