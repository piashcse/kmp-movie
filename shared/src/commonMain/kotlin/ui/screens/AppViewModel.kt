package ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.BaseModel
import data.model.MovieItem
import data.model.TvSeriesItem
import data.model.celebrities.Celebrity
import data.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import theme.ThemeMode
import utils.network.UiState

private const val SEARCH_DEBOUNCE_MS = 300L

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class AppViewModel(private val repo: Repository) : ViewModel() {

    private val _movieSearchData = MutableStateFlow<List<MovieItem>>(emptyList())
    val movieSearchData = _movieSearchData.asStateFlow()

    private val _tvSeriesSearchData = MutableStateFlow<List<TvSeriesItem>>(emptyList())
    val tvSeriesSearchData = _tvSeriesSearchData.asStateFlow()

    private val _celebritySearchData = MutableStateFlow<List<Celebrity>>(emptyList())
    val celebritySearchData = _celebritySearchData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode = _themeMode.asStateFlow()

    private val movieQuery = MutableStateFlow("")
    private val tvSeriesQuery = MutableStateFlow("")
    private val celebrityQuery = MutableStateFlow("")

    init {
        observeSearch(movieQuery, repo::searchMovie, _movieSearchData)
        observeSearch(tvSeriesQuery, repo::searchTvSeries, _tvSeriesSearchData)
        observeSearch(celebrityQuery, repo::searchCelebrity, _celebritySearchData)

        viewModelScope.launch {
            val saved = repo.getThemeMode()
            ThemeMode.entries.firstOrNull { it.name == saved }?.let { _themeMode.value = it }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        if (_themeMode.value == mode) return
        _themeMode.value = mode
        viewModelScope.launch { repo.setThemeMode(mode.name) }
    }

    fun movieSearch(searchKey: String) {
        movieQuery.value = searchKey
    }

    fun tvSeriesSearch(searchKey: String) {
        tvSeriesQuery.value = searchKey
    }

    fun celebritySearch(searchKey: String) {
        celebrityQuery.value = searchKey
    }

    /**
     * Debounces and cancels in-flight requests through [flatMapLatest], so typing a word
     * results in a single network call and out-of-order responses are impossible.
     * Clearing the query resets the result list immediately.
     */
    private fun <T> observeSearch(
        query: MutableStateFlow<String>,
        search: (String) -> Flow<UiState<BaseModel<T>>>,
        target: MutableStateFlow<List<T>>,
    ) {
        viewModelScope.launch {
            query
                .debounce(SEARCH_DEBOUNCE_MS)
                .map { it.trim() }
                .distinctUntilChanged()
                .flatMapLatest { term ->
                    if (term.isEmpty()) {
                        flowOf(UiState.Success(emptyList()))
                    } else {
                        search(term).map { it.mapResults() }
                    }
                }
                .collect { state ->
                    when (state) {
                        is UiState.Loading -> _isLoading.value = true
                        is UiState.Success -> {
                            target.value = state.data
                            _isLoading.value = false
                        }

                        is UiState.Error -> _isLoading.value = false
                    }
                }
        }
    }
}

private fun <T> UiState<BaseModel<T>>.mapResults(): UiState<List<T>> = when (this) {
    is UiState.Loading -> UiState.Loading
    is UiState.Success -> UiState.Success(data.results)
    is UiState.Error -> this
}
