package ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import theme.Blue
import ui.AppViewModel

@ExperimentalCoroutinesApi
@Composable
fun SearchBar(viewModel: AppViewModel, pressOnBack: () -> Unit) {
    var text by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Row(Modifier.background(color = Blue)) {
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            imageVector = Icons.Filled.ArrowBack,
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    pressOnBack()
                }
        )
        Spacer(modifier = Modifier.width(5.dp))
        TextField(modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            value = text,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Blue,
                cursorColor = Color.Black,
                disabledLabelColor = Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                text = it
                viewModel.searchApi(it)
            },
            //shape = RoundedCornerShape(8.dp),
            singleLine = true,
            trailingIcon = {
                if (text.trim().isNotEmpty()) {
                    Icon(Icons.Filled.Clear,
                        contentDescription = "clear text",
                        modifier = Modifier.padding(end = 16.dp).offset(x = 10.dp).clickable {
                            text = ""
                        })
                } else {
                    Icon(Icons.Filled.Search,
                        contentDescription = "search",
                        modifier = Modifier.padding(end = 16.dp).offset(x = 10.dp).clickable {

                        })
                }
            })
    }
}