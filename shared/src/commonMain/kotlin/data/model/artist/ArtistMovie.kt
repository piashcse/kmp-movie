package data.model.artist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistMovie(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("character")
    val character: String?,
    @SerialName("credit_id")
    val creditId: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>?,
    @SerialName("id")
    val id: Int,
    @SerialName("media_type")
    val mediaType: String?,
    @SerialName("order")
    val order: Int?,  // ✅ Made nullable
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_title")
    val originalTitle: String?,  // ✅ Made nullable
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,  // ✅ Made nullable
    @SerialName("title")
    val title: String?,  // ✅ Made nullable
    @SerialName("video")
    val video: Boolean?,  // ✅ Made nullable
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)