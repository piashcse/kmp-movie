package ui.screens.tv_series.detail

import androidx.compose.runtime.Immutable
import data.model.TvSeriesItem
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit

@Immutable
data class TvSeriesDetailUiState(
    val isLoading: Boolean = false,
    val tvSeriesDetail: TvSeriesDetail? = null,
    val recommendedTvSeries: List<TvSeriesItem> = emptyList(),
    val creditTvSeries: Credit? = null,
    val errorMessage: String? = null
)