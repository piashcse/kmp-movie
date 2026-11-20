package navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class NavigatorTest {

    @Test
    fun `route keys round trip`() {
        val routes = listOf(
            NowPlayingMovie,
            PopularMovie,
            TopRatedMovie,
            UpcomingMovie,
            AiringTodayTvSeries,
            OnTheAirTvSeries,
            PopularTvSeries,
            TopRatedTvSeries,
            TrendingCelebrity,
            PopularCelebrity,
            FavoriteMovie,
            FavoriteTvSeries,
            FavoriteCelebrity,
            MovieDetail(11),
            TvSeriesDetail(22),
            ArtistDetail(33),
            Search,
        )

        routes.forEach { route ->
            assertEquals(route, decodeRoute(route.saveKey()))
        }
    }

    @Test
    fun `unknown keys are ignored`() {
        assertEquals(null, decodeRoute("unknown:1"))
        assertEquals(null, decodeRoute("movie:not-an-int"))
    }
}
