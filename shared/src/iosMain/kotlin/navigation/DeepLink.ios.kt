package navigation

import androidx.compose.runtime.Composable

private object NoOpDeepLinkController : DeepLinkController {
    override fun initialRoute(): Route? = null
    override fun onRouteChanged(route: Route) = Unit
    override fun observe(onRoute: (Route) -> Unit): () -> Unit = {}
}

@Composable
actual fun rememberDeepLinkController(): DeepLinkController = NoOpDeepLinkController
