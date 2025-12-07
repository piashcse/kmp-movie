package utils

import kotlinx.coroutines.flow.flow
import utils.network.UiState

// Helper for endpoints that return BaseModel with .results
inline fun <T> flowWithResults(
    crossinline apiCall: suspend () -> data.model.BaseModel<T>
) = flow {
    try {
        emit(UiState.Loading)
        val result = apiCall()
        emit(UiState.Success(result.results))
    } catch (e: Exception) {
        emit(UiState.Error(e))
    }
}

// Helper for endpoints that return direct objects
inline fun <T> flowDirect(
    crossinline apiCall: suspend () -> T
) = flow {
    try {
        emit(UiState.Loading)
        val result = apiCall()
        emit(UiState.Success(result))
    } catch (e: Exception) {
        emit(UiState.Error(e))
    }
}