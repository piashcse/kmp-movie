package data.model.video

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class VideoTest {

    private fun video(key: String, type: String, site: String = "YouTube", official: Boolean = false) =
        VideoItem(key = key, type = type, site = site, official = official)

    @Test
    fun `official trailer wins over teaser and other videos`() {
        val videos = listOf(
            video("clip", "Clip"),
            video("teaser", "Teaser"),
            video("official", "Trailer", official = true),
            video("trailer", "Trailer"),
        )

        assertEquals("official", videos.bestTrailer()?.key)
    }

    @Test
    fun `fallback order is trailer then teaser then any youtube video`() {
        assertEquals(
            "trailer",
            listOf(video("clip", "Clip"), video("trailer", "Trailer")).bestTrailer()?.key,
        )
        assertEquals(
            "teaser",
            listOf(video("clip", "Clip"), video("teaser", "Teaser")).bestTrailer()?.key,
        )
        assertEquals("clip", listOf(video("clip", "Clip")).bestTrailer()?.key)
    }

    @Test
    fun `non youtube videos are ignored`() {
        val videos = listOf(video("vimeo", "Trailer", site = "Vimeo"))
        assertNull(videos.bestTrailer())
    }

    @Test
    fun `trailer url points at youtube`() {
        assertEquals("https://www.youtube.com/watch?v=abc123", video("abc123", "Trailer").watchUrl())
    }
}
