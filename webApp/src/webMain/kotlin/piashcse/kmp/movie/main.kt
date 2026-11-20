package piashcse.kmp.movie

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import di.initKoinPlatform
import kotlinx.browser.document
import ui.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoinPlatform()
    ComposeViewport(document.body!!) {
        App()
    }
}
