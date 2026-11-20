package constant

import kotlin.test.Test
import kotlin.test.assertEquals

class AppConstantTest {

    @Test
    fun `image url is empty when the path is missing`() {
        assertEquals("", AppConstant.imageUrl(null))
        assertEquals("", AppConstant.imageUrl(""))
        assertEquals("", AppConstant.imageUrl("   "))
    }

    @Test
    fun `image url uses the requested size`() {
        assertEquals(
            "https://image.tmdb.org/t/p/w342/poster.jpg",
            AppConstant.imageUrl("/poster.jpg"),
        )
        assertEquals(
            "https://image.tmdb.org/t/p/w780/backdrop.jpg",
            AppConstant.imageUrl("/backdrop.jpg", size = "w780"),
        )
    }
}
