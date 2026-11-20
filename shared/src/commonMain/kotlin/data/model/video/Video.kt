package data.model.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResults(
    val results: List<VideoItem> = emptyList(),
)

@Serializable
data class VideoItem(
    val id: String = "",
    val key: String = "",
    val name: String = "",
    val site: String = "",
    val type: String = "",
    @SerialName("official") val official: Boolean = false,
)

/** YouTube trailers first, then teasers, then any other YouTube video. */
fun List<VideoItem>.bestTrailer(): VideoItem? {
    val youtube = filter { it.site.equals("YouTube", ignoreCase = true) && it.key.isNotBlank() }
    return youtube.firstOrNull { it.type.equals("Trailer", ignoreCase = true) && it.official }
        ?: youtube.firstOrNull { it.type.equals("Trailer", ignoreCase = true) }
        ?: youtube.firstOrNull { it.type.equals("Teaser", ignoreCase = true) }
        ?: youtube.firstOrNull()
}

fun VideoItem.watchUrl(): String = "https://www.youtube.com/watch?v=$key"
