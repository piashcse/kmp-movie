package ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import theme.Blue
import ui.screens.AppViewModel

@OptIn(FlowPreview::class, ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun SearchBar(
    viewModel: AppViewModel,
    searchFilter: String,
    isLoading: Boolean,
    pressOnBack: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Row(Modifier.background(color = Blue)) {
        TextField(
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            value = text,
            placeholder = {
                androidx.compose.material.Text(
                    text = when (searchFilter) {
                        "Movies" -> "Search movies..."
                        "TV Series" -> "Search TV series..."
                        "Celebrities" -> "Search celebrities..."
                        else -> "Search movies, TV series, celebrities..."
                    },
                    color = Color.White.copy(alpha = 0.7f)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primarySurface,
                cursorColor = Color.White,
                disabledLabelColor = Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color.White
            ),
            onValueChange = { newValue ->
                text = newValue
                // Perform search based on the active filter
                when (searchFilter) {
                    "Movies" -> viewModel.movieSearch(newValue)
                    "TV Series" -> viewModel.tvSeriesSearch(newValue)
                    "Celebrities" -> viewModel.celebritySearch(newValue)
                    else -> {
                        // Default to movies for "All" filter
                        viewModel.movieSearch(newValue)
                    }
                }
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "search",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
            trailingIcon = {
                if (text.trim().isNotEmpty()) {
                    if (isLoading) {
                        // Show loading indicator when search is in progress
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        // Show clear icon when not loading
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "clear text",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable {
                                    text = ""
                                }
                        )
                    }
                }
            }
        )
    }
}