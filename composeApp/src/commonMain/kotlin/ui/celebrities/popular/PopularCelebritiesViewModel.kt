package ui.celebrities.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.celebrities.Celebrity
import data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ui.celebrities.CelebrityUiState
import utils.Paginator

class PopularCelebritiesViewModel(private val repo: Repository = Repository()) : ViewModel() {

    private val _uiState = MutableStateFlow(CelebrityUiState())
    val uiState: StateFlow<CelebrityUiState> get() = _uiState.asStateFlow()

    private val paginator = Paginator<Celebrity>(
        scope = viewModelScope,
        initialKey = 1,
        incrementBy = 1,
        onLoadUpdated = { isLoading ->
            _uiState.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextKey ->
            repo.popularCelebrities(nextKey)
        },
        onError = { throwable ->
            _uiState.update { it.copy(errorMessage = throwable.message) }
        },
        onSuccess = { items, _ ->
            _uiState.update { current ->
                current.copy(celebrityList = current.celebrityList.orEmpty() + items)
            }
        }
    )

    fun loadPopularCelebrities() {
        paginator.loadNextItems()
    }
}