package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.action_back
import kmp_movie.composeapp.generated.resources.action_clear
import kmp_movie.composeapp.generated.resources.action_search
import kmp_movie.composeapp.generated.resources.search_celebrities
import kmp_movie.composeapp.generated.resources.search_movies
import kmp_movie.composeapp.generated.resources.search_tv_series
import org.jetbrains.compose.resources.stringResource
import ui.screens.AppViewModel

@Composable
fun SearchBar(
    viewModel: AppViewModel,
    searchFilter: SearchFilter,
    isLoading: Boolean,
    onBack: () -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val placeholder = stringResource(
        when (searchFilter) {
            SearchFilter.MOVIES -> Res.string.search_movies
            SearchFilter.TV_SERIES -> Res.string.search_tv_series
            SearchFilter.CELEBRITIES -> Res.string.search_celebrities
        }
    )
    val contentColor = MaterialTheme.colorScheme.onPrimary

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.action_back),
                tint = contentColor,
            )
        }
        TextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            value = text,
            placeholder = {
                Text(
                    text = placeholder,
                    color = contentColor.copy(alpha = 0.7f),
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = contentColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = contentColor,
                unfocusedTextColor = contentColor,
            ),
            onValueChange = { newValue ->
                text = newValue
                when (searchFilter) {
                    SearchFilter.MOVIES -> viewModel.movieSearch(newValue)
                    SearchFilter.TV_SERIES -> viewModel.tvSeriesSearch(newValue)
                    SearchFilter.CELEBRITIES -> viewModel.celebritySearch(newValue)
                }
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(Res.string.action_search),
                    tint = contentColor.copy(alpha = 0.7f),
                    modifier = Modifier.padding(start = 8.dp),
                )
            },
            trailingIcon = {
                if (text.trim().isNotEmpty()) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp),
                            color = contentColor,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        IconButton(
                            onClick = {
                                text = ""
                                when (searchFilter) {
                                    SearchFilter.MOVIES -> viewModel.movieSearch("")
                                    SearchFilter.TV_SERIES -> viewModel.tvSeriesSearch("")
                                    SearchFilter.CELEBRITIES -> viewModel.celebritySearch("")
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(Res.string.action_clear),
                                tint = contentColor,
                            )
                        }
                    }
                }
            },
        )
    }
}
