package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.window
import org.w3c.dom.events.Event

private class WebDeepLinkController : DeepLinkController {
    override fun initialRoute(): Route? = routeFromDeepLink(window.location.hash)

    override fun onRouteChanged(route: Route) {
        val hash = "#/${route.toDeepLink()}"
        if (window.location.hash != hash) {
            window.location.hash = hash
        }
    }

    override fun observe(onRoute: (Route) -> Unit): () -> Unit {
        val listener: (Event) -> Unit = {
            routeFromDeepLink(window.location.hash)?.let(onRoute)
        }
        window.addEventListener("hashchange", listener)
        return { window.removeEventListener("hashchange", listener) }
    }
}

@Composable
actual fun rememberDeepLinkController(): DeepLinkController = remember { WebDeepLinkController() }
