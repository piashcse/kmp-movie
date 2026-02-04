package data.model.movie_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ui.component.GenreItem

@Serializable
data class Genre(
    @SerialName("id")
    override val id: Int,

    @SerialName("name")
    override val name: String
) : GenreItem