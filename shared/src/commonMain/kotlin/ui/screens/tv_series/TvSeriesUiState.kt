package ui.screens.tv_series

import androidx.compose.runtime.Immutable
import data.model.TvSeriesItem

@Immutable
data class TvSeriesUiState(
    val tvSeriesList: List<TvSeriesItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
