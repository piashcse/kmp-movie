package data.model.artist

import kotlinx.serialization.Serializable

@Serializable
data class ArtistMovie(
    val adult: Boolean,
    val backdropPath: String?,
    val character: String?,
    val creditId: String?,
    val genreIds: List<Int>?,
    val id: Int,
    val mediaType: String?,
    val order: Int?,  // ✅ Made nullable
    val originalLanguage: String?,
    val originalTitle: String?,  // ✅ Made nullable
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,  // ✅ Made nullable
    val title: String?,  // ✅ Made nullable
    val video: Boolean?,  // ✅ Made nullable
    val voteAverage: Double?,
    val voteCount: Int?
)
