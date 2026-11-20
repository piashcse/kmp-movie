package data.model.movie_detail

import data.model.BaseModel
import data.model.Genre
import data.model.MovieItem
import data.model.ProductionCompany
import data.model.ProductionCountry
import data.model.SpokenLanguage
import data.model.artist.Artist
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val adult: Boolean,

    val backdropPath: String?,

    val belongsToCollection: BelongsToCollection?,

    val budget: Int,

    val genres: List<Genre>,

    val homepage: String,

    val id: Int,

    val imdbId: String?,

    val originalLanguage: String,

    val originalTitle: String,

    val overview: String,

    val popularity: Double,

    val posterPath: String?,

    val productionCompanies: List<ProductionCompany>,

    val productionCountries: List<ProductionCountry>,

    val releaseDate: String,

    val revenue: Int,

    val runtime: Int,

    val spokenLanguages: List<SpokenLanguage>,

    val status: String,

    val tagline: String,

    val title: String,

    val video: Boolean,

    val voteAverage: Double,

    val voteCount: Int,

    val credits: Artist? = null,

    val recommendations: BaseModel<MovieItem>? = null,
)
