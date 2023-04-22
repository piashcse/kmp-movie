package data.remote.toprated

import data.model.BaseModelV2

interface TopRatedApiInterface {
    suspend fun topRatedMovieList(
         page: Int
    ): BaseModelV2
}