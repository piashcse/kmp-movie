package utils.network

/**
 * Data state for processing api response Loading, Success and Error
 */
sealed class UiState<out R> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val exception: Exception) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
}