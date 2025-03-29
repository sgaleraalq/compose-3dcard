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

import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
public fun Compose3DCard(
    modifier: Modifier = Modifier,
    frontImage: Int,
    backImage: Int,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    colors: Compose3DCardColors = Compose3DCardColors()
) {
    val density = LocalDensity.current

    var size by remember { mutableStateOf(IntSize.Zero) }
    var rotationX by remember { mutableFloatStateOf(0f) }
    var rotationY by remember { mutableFloatStateOf(0f) }

    var isFlipped by remember { mutableStateOf(false) }
    var totalDragX by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures (
                    onDrag = { change, dragAmount ->
                        change.consume()
                        totalDragX += dragAmount.x
                        // Movement of card in 3D space
                        rotationY = (rotationY + dragAmount.x * 0.1f).coerceIn(-10f, 10f)
                        rotationX = (rotationX - dragAmount.y * 0.1f).coerceIn(-10f, 10f)
                    },
                    onDragStart = {
                        totalDragX = 0f
                    },
                    onDragEnd = {
                        Log.i("Compose3DCard", "onDragEnd: $totalDragX ${size.width}")
                        if (abs(totalDragX) >= size.width/2) {
                            isFlipped = !isFlipped
                        }
                    }
                )
            }
            .graphicsLayer(
                rotationX = rotationX,
                rotationY = rotationY,
                transformOrigin = TransformOrigin.Center,
                cameraDistance = 12f * density.density
            )
            .clip(shape)
            .onGloballyPositioned { layoutCoordinates ->
                size = layoutCoordinates.size
            }// TODO background
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(if (!isFlipped) frontImage else backImage),
            contentDescription = null
        )
    }
//
//
//    HazeEffect(
//        modifier = modifier,
//        density = density,
//        size = size,
//        rotationX = rotationX,
//        rotationY = rotationY,
//        shape = shape
//    )
//
//    ShimmerEffect(
//        modifier = modifier,
//        density = density,
//        size = size,
//        rotationX = rotationX,
//        rotationY = rotationY,
//        shape = shape
//    )
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

@Composable
public fun ShimmerEffect(
    modifier: Modifier,
    density: Density,
    size: IntSize,
    rotationX: Float,
    rotationY: Float,
    shape: RoundedCornerShape
) {
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
            .clip(shape)
            .shimmerEffect()
    )
}


public fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -3 * size.width.toFloat(),
        targetValue = 3 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 8000,
                delayMillis = 2000
            )
        )
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
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}