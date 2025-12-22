package ui.screens.common

import data.repository.Repository
import ui.screens.base.PaginatedViewModel
import utils.network.UiState

/**
 * Generic ViewModel that can handle different types of paginated data
 * This replaces the need for individual ViewModels like NowPlayingViewModel, PopularMovieViewModel, etc.
 */
abstract class GenericPaginatedViewModel<T>(
    protected val repo: Repository
) : PaginatedViewModel<T, GenericUiState<T>>(
    initialState = GenericUiState(),
    updateItems = { state, items -> state.copy(items = items) },
    getItems = { it.items }
) {
    override fun updateLoading(state: GenericUiState<T>, isLoading: Boolean) = state.copy(isLoading = isLoading)
    override fun updateError(state: GenericUiState<T>, error: String?) = state.copy(errorMessage = error)
}