package navigation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DeepLinkTest {

    @Test
    fun `detail links parse from url fragments`() {
        assertEquals(MovieDetail(27205), routeFromDeepLink("#/movie/27205"))
        assertEquals(TvSeriesDetail(1399), routeFromDeepLink("/series/1399"))
        assertEquals(ArtistDetail(31), routeFromDeepLink("person/31"))
    }

    @Test
    fun `top level links parse with and without hash`() {
        assertEquals(PopularMovie, routeFromDeepLink("#/movies/popular"))
        assertEquals(TopRatedTvSeries, routeFromDeepLink("tv/top-rated/"))
        assertEquals(FavoriteCelebrity, routeFromDeepLink("#/favorites/celebrities"))
    }

    @Test
    fun `search is a single segment link`() {
        assertEquals(Search, routeFromDeepLink("search"))
        assertEquals(Search, routeFromDeepLink("#/search/"))
    }

    @Test
    fun `invalid links are ignored`() {
        assertNull(routeFromDeepLink(""))
        assertNull(routeFromDeepLink("#/"))
        assertNull(routeFromDeepLink("movie/not-a-number"))
        assertNull(routeFromDeepLink("unknown/1"))
        assertNull(routeFromDeepLink("movie"))
    }

    @Test
    fun `routes round trip through deep links`() {
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
            assertEquals(route, routeFromDeepLink(route.toDeepLink()))
        }
    }
}
