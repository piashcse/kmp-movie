package piashcse.kmp.movie

import ui.App
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.coroutines.ExperimentalCoroutinesApi
import di.initKoinPlatform
import di.KoinApplication

@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoroutinesApi::class)
fun main() {
    initKoinPlatform()
    ComposeViewport(document.body!!) {
        KoinApplication {
            App()
        }
    }
}