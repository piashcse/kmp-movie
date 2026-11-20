package ui.component.skeleton

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import theme.Spacing

/**
 * Single shimmer brush shared by all placeholders on a screen. Creating one transition per
 * skeleton (instead of per cell) keeps the animation cheap.
 */
@Composable
private fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "skeleton_shimmer")
    val shift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "skeleton_shift",
    )

    val base = MaterialTheme.colorScheme.surfaceVariant
    val highlight = MaterialTheme.colorScheme.surface
    return remember(base, highlight, shift) {
        Brush.linearGradient(
            colors = listOf(base, highlight, base),
            start = Offset(shift, 0f),
            end = Offset(shift + 400f, 400f),
        )
    }
}

@Composable
private fun SkeletonBox(
    brush: Brush,
    modifier: Modifier,
) {
    Box(modifier = modifier.background(brush))
}

/** Placeholder grid shown while the first page loads. */
@Composable
fun MediaGridSkeleton(
    modifier: Modifier = Modifier,
    imageHeight: Dp = 250.dp,
    itemCount: Int = 8,
) {
    val brush = rememberShimmerBrush()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 180.dp),
        userScrollEnabled = false,
        modifier = modifier
            .fillMaxSize()
            .padding(start = Spacing.sm, end = Spacing.sm, top = Spacing.md)
            .clearAndSetSemantics {},
    ) {
        items(itemCount) {
            Column(modifier = Modifier.padding(Spacing.xs)) {
                SkeletonBox(
                    brush = brush,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .clip(MaterialTheme.shapes.medium),
                )
                SkeletonBox(
                    brush = brush,
                    modifier = Modifier
                        .padding(top = Spacing.sm)
                        .fillMaxWidth(0.85f)
                        .height(14.dp)
                        .clip(MaterialTheme.shapes.small),
                )
                SkeletonBox(
                    brush = brush,
                    modifier = Modifier
                        .padding(top = Spacing.xs)
                        .fillMaxWidth(0.5f)
                        .height(12.dp)
                        .clip(MaterialTheme.shapes.small),
                )
            }
        }
    }
}

/** Placeholder for the movie/TV detail screen while its first request is in flight. */
@Composable
fun MediaDetailSkeleton(
    modifier: Modifier = Modifier,
) {
    val brush = rememberShimmerBrush()

    Column(
        modifier = modifier
            .fillMaxSize()
            .clearAndSetSemantics {},
    ) {
        SkeletonBox(
            brush = brush,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
        )
        Row(
            modifier = Modifier.padding(Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md),
        ) {
            SkeletonBox(
                brush = brush,
                modifier = Modifier
                    .size(110.dp, 150.dp)
                    .clip(MaterialTheme.shapes.medium),
            )
            Column(modifier = Modifier.weight(1f)) {
                SkeletonBox(
                    brush = brush,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.small),
                )
                repeat(4) {
                    SkeletonBox(
                        brush = brush,
                        modifier = Modifier
                            .padding(top = Spacing.sm)
                            .fillMaxWidth(0.6f)
                            .height(12.dp)
                            .clip(MaterialTheme.shapes.small),
                    )
                }
            }
        }
        Column(modifier = Modifier.padding(horizontal = Spacing.md)) {
            SkeletonBox(
                brush = brush,
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .height(18.dp)
                    .clip(MaterialTheme.shapes.small),
            )
            repeat(3) {
                SkeletonBox(
                    brush = brush,
                    modifier = Modifier
                        .padding(top = Spacing.sm)
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(MaterialTheme.shapes.small),
                )
            }
        }
    }
}
