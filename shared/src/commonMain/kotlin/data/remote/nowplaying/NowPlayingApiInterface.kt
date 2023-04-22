package data.remote.nowplaying

import data.model.BaseModel

interface NowPlayingApiInterface {
    suspend fun nowPlayingMovieList(
         page: Int
    ): BaseModel
}