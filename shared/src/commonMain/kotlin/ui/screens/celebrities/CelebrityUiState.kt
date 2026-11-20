package ui.screens.celebrities

import androidx.compose.runtime.Immutable
import data.model.celebrities.Celebrity

@Immutable
data class CelebrityUiState(
    val celebrityList: List<Celebrity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
