package ui.screens

import kotlin.test.Test
import kotlin.test.assertNotNull

class AppViewModelTest {
    @Test
    fun testAppViewModelCreation() {
        // Basic test to ensure AppViewModel can be instantiated
        val viewModel = AppViewModel()
        assertNotNull(viewModel)
    }
}