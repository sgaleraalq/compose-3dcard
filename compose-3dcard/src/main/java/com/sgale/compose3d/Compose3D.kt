/*
 * Designed and developed by 2025 sgaleraalq (Sergio Galera)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sgale.compose3d

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
public fun Compose3DCard(
    modifier: Modifier = Modifier,
    frontImage: Int,
    backImage: Int? = null,
    contentScale: ContentScale = ContentScale.Fit,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    colors: Compose3DCardColors = Compose3DCardColors(),
    shimmerDirection: ShimmerDirection = ShimmerDirection.TOP_LEFT_TO_BOTTOM_RIGHT
) {
    val density = LocalDensity.current

    var size by remember { mutableStateOf(IntSize.Zero) }
    val flipController = remember { FlipController() }

    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            if (flipController.isFlipping) return@detectDragGestures
                            change.consume()
                            flipController.updateRotation(dragAmount.x, dragAmount.y)
                        },
                        onDragStart = { flipController.resetDragging() },
                        onDragEnd = { flipController.endDragging() }
                    )
                }
                .graphicsLayer(
                    rotationX = flipController.rotationX.value,
                    rotationY = flipController.rotationY.value,
                    transformOrigin = TransformOrigin.Center,
                    cameraDistance = 12f * density.density
                )
                .onGloballyPositioned { layoutCoordinates ->
                    size = layoutCoordinates.size
                    flipController.updateSize(layoutCoordinates.size)
                }
                .padding(12.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = shape,
                    ambientColor = Red
                )
                .clip(shape),
            painter = painterResource(
                if (!flipController.isFlipped) frontImage else backImage ?: frontImage
            ),
            contentDescription = null,
            contentScale = contentScale
        )

        if (!flipController.isFlipped) {
            ShimmerEffect(
                size = size,
                rotationX = flipController.rotationX.value,
                rotationY = flipController.rotationY.value,
                shape = shape,
                shimmerDirection = shimmerDirection
            )
        }
    }
}

@Composable
public fun ShimmerEffect(
    size: IntSize,
    rotationX: Float,
    rotationY: Float,
    shape: RoundedCornerShape,
    shimmerDirection: ShimmerDirection
) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .size(
                width = with(density) { size.width.toDp() },
                height = with(density) { size.height.toDp() }
            )
            .graphicsLayer(
                rotationX = rotationX,
                rotationY = rotationY,
                transformOrigin = TransformOrigin.Center,
                cameraDistance = 12f * density.density
            )
            .padding(12.dp)
            .clip(shape)
            .shimmerEffect(shimmerDirection)
    )
}

public fun Modifier.shimmerEffect(
    shimmerDirection: ShimmerDirection
): Modifier = composed {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()

    val directionMultiplier = when (shimmerDirection) {
        ShimmerDirection.TOP_LEFT_TO_BOTTOM_RIGHT, ShimmerDirection.BOTTOM_LEFT_TO_TOP_RIGHT -> -1
        ShimmerDirection.TOP_RIGHT_TO_BOTTOM_LEFT, ShimmerDirection.BOTTOM_RIGHT_TO_TOP_LEFT -> 1
    }

    val initialValue = 3 * directionMultiplier * size.width.toFloat()
    val targetValue = -initialValue

    val startOffsetX by transition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                delayMillis = 0
            )
        )
    )

    val endOffset = Offset(
        x = startOffsetX + directionMultiplier * size.width.toFloat(),
        y = when (shimmerDirection) {
            ShimmerDirection.TOP_LEFT_TO_BOTTOM_RIGHT, ShimmerDirection.TOP_RIGHT_TO_BOTTOM_LEFT -> size.height.toFloat()
            ShimmerDirection.BOTTOM_LEFT_TO_TOP_RIGHT, ShimmerDirection.BOTTOM_RIGHT_TO_TOP_LEFT -> -size.height.toFloat()
        }
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Transparent,
                Transparent,
                Color(0x55FFFFFF),
                Transparent,
                Transparent
            ),
            start = Offset(startOffsetX, 0f),
            end = endOffset
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Composable
public fun HazeEffect(
    modifier: Modifier = Modifier,
    density: Density,
    size: IntSize,
    rotationX: Float,
    rotationY: Float,
    shape: RoundedCornerShape
) {
    val transition = rememberInfiniteTransition()

    val animatedOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val animatedBrush = Brush.linearGradient(
        colors = listOf(Blue, Yellow, Red),
        start = Offset.Zero,
        end = Offset(x = size.width * animatedOffset, y = size.height * animatedOffset)
    )

    Box(
        modifier = modifier
            .size(
                width = with(density) { size.width.toDp() },
                height = with(density) { size.height.toDp() }
            )
            .graphicsLayer(
                rotationX = rotationX,
                rotationY = rotationY,
                transformOrigin = TransformOrigin.Center,
                cameraDistance = 12f * density.density
            )
            .border(
                width = 2.dp,
                brush = animatedBrush,
                shape = shape
            )
    )
}