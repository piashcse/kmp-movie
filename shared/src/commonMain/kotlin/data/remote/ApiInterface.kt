package data.remote

import data.remote.model.BaseModel

interface ApiInterface {
    suspend fun nowPlayingMovieList(
         page: Int
    ): BaseModel
}