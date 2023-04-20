package data.remote

import data.model.BaseModel

interface NowPlayingApiInterface {
    suspend fun nowPlayingMovieList(
         page: Int
    ): BaseModel
}