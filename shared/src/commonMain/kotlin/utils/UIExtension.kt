package utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

fun Modifier.cornerRadius(radius: Int) =
    graphicsLayer(shape = RoundedCornerShape(radius.dp), clip = true)