package ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import data.model.BaseModelV2
import data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.network.DataState

@ExperimentalCoroutinesApi
class AppViewModel: ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val repo = MovieRepository()
    val searchData: MutableState<DataState<BaseModelV2>?> = mutableStateOf(null)
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchApi(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    repo.searchMovie(it)
                }.collect {
                    if (it is DataState.Success){
                        it.data
                    }
                    searchData.value = it
                }
        }
    }
}