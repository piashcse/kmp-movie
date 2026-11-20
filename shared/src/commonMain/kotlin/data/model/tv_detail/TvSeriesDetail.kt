package data.model.tv_detail


import data.model.BaseModel
import data.model.Genre
import data.model.TvSeriesItem
import data.model.ProductionCompany
import data.model.ProductionCountry
import data.model.SpokenLanguage
import data.model.tv_detail.credit.Credit
import data.model.video.VideoResults
import data.model.watch_provider.WatchProviders
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvSeriesDetail(
    val adult: Boolean,
    val backdropPath: String,
    val createdBy: List<CreatedBy>,
    val episodeRunTime: List<Int>,
    val firstAirDate: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String?,
    val lastEpisodeToAir: LastEpisodeToAir?,
    val name: String,
    val networks: List<Network>,
    val nextEpisodeToAir: NextEpisodeToAir?,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val seasons: List<Season>,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Int,

    val credits: Credit? = null,

    val recommendations: BaseModel<TvSeriesItem>? = null,

    val videos: VideoResults? = null,

    @SerialName("watch/providers") val watchProviders: WatchProviders? = null,
)
