package ui.screens.tv_series

import data.model.TvSeriesItem

data class TvSeriesUiState(
    val tvSeriesList: List<TvSeriesItem>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
