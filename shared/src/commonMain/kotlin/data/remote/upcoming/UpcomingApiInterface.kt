package data.remote.upcoming

import data.model.BaseModel

interface UpcomingApiInterface {
    suspend fun upcomingMovieList(
         page: Int
    ): BaseModel
}