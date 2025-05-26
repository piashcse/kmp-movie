package ui.tv_series.detail

import data.model.TvSeriesItem
import data.model.tv_detail.TvSeriesDetail
import data.model.tv_detail.credit.Credit

data class TvSeriesDetailUiState(
    val isLoading: Boolean = false,
    val tvSeriesDetail: TvSeriesDetail? = null,
    val recommendedTvSeries: List<TvSeriesItem> = emptyList(),
    val creditTvSeries: Credit? = null,
    val errorMessage: String? = null
)