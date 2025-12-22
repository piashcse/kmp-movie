package utils

import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.flow
import utils.network.UiState

// Helper for endpoints that return BaseModel with .results
inline fun <T> flowWithResults(
    crossinline apiCall: suspend () -> ApiResponse<data.model.BaseModel<T>>
) = flow {
    emit(UiState.Loading)
    apiCall().let { response ->
        when (response) {
            is ApiResponse.Success -> emit(UiState.Success(response.data.results))
            is ApiResponse.Failure.Error -> emit(UiState.Error(Exception("HTTP Error occurred")))
            is ApiResponse.Failure.Exception -> emit(UiState.Error(Exception("Network exception occurred")))
        }
    }
}

// Helper for endpoints that return direct objects
inline fun <T> flowDirect(
    crossinline apiCall: suspend () -> ApiResponse<T>
) = flow {
    emit(UiState.Loading)
    apiCall().let { response ->
        when (response) {
            is ApiResponse.Success -> emit(UiState.Success(response.data))
            is ApiResponse.Failure.Error -> emit(UiState.Error(Exception("HTTP Error occurred")))
            is ApiResponse.Failure.Exception -> emit(UiState.Error(Exception("Network exception occurred")))
        }
    }
}