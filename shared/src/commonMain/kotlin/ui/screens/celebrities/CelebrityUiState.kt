package ui.screens.celebrities

import data.model.celebrities.Celebrity

data class CelebrityUiState(
    val celebrityList: List<Celebrity>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)