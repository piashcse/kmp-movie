package data.remote.pupular

import data.model.BaseModelV2

interface PopularApiInterface {
    suspend fun popularMovieList(
         page: Int
    ): BaseModelV2
}