package piashcse.kmp.movie

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import di.KoinApplication
import di.initKoinPlatform
import kotlinx.browser.document
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.App

@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoroutinesApi::class)
fun main() {
    initKoinPlatform()
    ComposeViewport(document.body!!) {
        KoinApplication {
            App()
        }
    }
}