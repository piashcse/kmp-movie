package utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import utils.network.DataState

fun Modifier.cornerRadius(radius: Int) =
    graphicsLayer(shape = RoundedCornerShape(radius.dp), clip = true)

fun <T : Any> MutableState<DataState<T>?>.pagingLoadingState(isLoaded: (pagingState: Boolean) -> Unit) {
    when (this.value) {
        is DataState.Success<T> -> {
            isLoaded(false)
        }

        is DataState.Loading -> {
            isLoaded(true)
        }

        is DataState.Error -> {
            isLoaded(false)
        }

        else -> {
            isLoaded(false)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun isCompactSize(): Boolean {
    val windowSizeClass = calculateWindowSizeClass()
    return when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> true
        else -> false
    }
}