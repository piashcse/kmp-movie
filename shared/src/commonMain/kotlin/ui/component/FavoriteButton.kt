package ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kmp_movie.composeapp.generated.resources.Res
import kmp_movie.composeapp.generated.resources.add_favorite
import kmp_movie.composeapp.generated.resources.remove_favorite
import org.jetbrains.compose.resources.stringResource

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color? = null,
) {
    val tint by animateColorAsState(
        targetValue = tint ?: if (isFavorite) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(durationMillis = 300),
        label = "favorite_tint_animation",
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(
                if (isFavorite) Res.string.remove_favorite else Res.string.add_favorite
            ),
            tint = tint
        )
    }
}
