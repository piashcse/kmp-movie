package utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import utils.network.UiState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PaginatorTest {

    @Test
    fun `stops requesting after an empty page`() = runTest {
        val requestedPages = mutableListOf<Int>()
        val receivedItems = mutableListOf<Int>()
        var loading = false

        val paginator = Paginator<Int>(
            scope = this,
            onLoadUpdated = { loading = it },
            onRequest = { page ->
                requestedPages += page
                flowOf(
                    if (page == 1) UiState.Success(listOf(1, 2))
                    else UiState.Success(emptyList())
                )
            },
            onError = {},
            onSuccess = { items, _ -> receivedItems += items },
        )

        paginator.loadNextItems()
        advanceUntilIdle()
        assertEquals(listOf(1), requestedPages)
        assertEquals(listOf(1, 2), receivedItems)
        assertFalse(loading)

        paginator.loadNextItems()
        advanceUntilIdle()
        assertEquals(listOf(1, 2), requestedPages)
        assertEquals(listOf(1, 2), receivedItems)

        // The empty page marked the end of the list, so no further request is made.
        paginator.loadNextItems()
        advanceUntilIdle()
        assertEquals(listOf(1, 2), requestedPages)
    }

    @Test
    fun `reset restarts from the first page`() = runTest {
        val requestedPages = mutableListOf<Int>()
        val paginator = Paginator<Int>(
            scope = this,
            onLoadUpdated = {},
            onRequest = { page ->
                requestedPages += page
                flowOf(UiState.Success(listOf(page)))
            },
            onError = {},
            onSuccess = { _, _ -> },
        )

        paginator.loadNextItems()
        advanceUntilIdle()
        paginator.loadNextItems()
        advanceUntilIdle()
        assertEquals(listOf(1, 2), requestedPages)

        paginator.reset()
        paginator.loadNextItems()
        advanceUntilIdle()
        assertEquals(listOf(1, 2, 1), requestedPages)
    }

    @Test
    fun `errors keep the current page so the request can be retried`() = runTest {
        val requestedPages = mutableListOf<Int>()
        var errorSeen = false

        val paginator = Paginator<Int>(
            scope = this,
            onLoadUpdated = {},
            onRequest = { page ->
                requestedPages += page
                if (requestedPages.size == 1) {
                    flowOf(UiState.Error(IllegalStateException("boom")))
                } else {
                    flowOf(UiState.Success(listOf(1)))
                }
            },
            onError = { errorSeen = true },
            onSuccess = { _, _ -> },
        )

        paginator.loadNextItems()
        advanceUntilIdle()
        assertTrue(errorSeen)

        paginator.loadNextItems()
        advanceUntilIdle()
        assertEquals(listOf(1, 1), requestedPages)
    }
}
