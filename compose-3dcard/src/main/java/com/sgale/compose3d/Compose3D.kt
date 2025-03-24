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
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
public fun Compose3DCard(
    modifier: Modifier = Modifier,
    img: Int,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp)
) {
    val density = LocalDensity.current

    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    var rotationX by remember { mutableFloatStateOf(0f) }
    var rotationY by remember { mutableFloatStateOf(0f) }

    Image(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    if (pan.x > 300f || pan.y > 300f) {
                        rotationX += 720f
                        rotationY += 720f
                    } else {
                        rotationY = (rotationY + pan.x * 0.1f).coerceIn(-10f, 10f)
                        rotationX = (rotationX - pan.y * 0.1f).coerceIn(-10f, 10f)
                    }
                }
            }
            .graphicsLayer(
                rotationX = rotationX,
                rotationY = rotationY,
                transformOrigin = TransformOrigin.Center,
                cameraDistance = 12f * density.density
            )
            .shadow(
                elevation = 16.dp,
                shape = shape
            )
            .clip(shape)
            .onGloballyPositioned { layoutCoordinates ->
                val positionInRoot = layoutCoordinates.positionInRoot()
                offset = Offset(positionInRoot.x, positionInRoot.y)
                size = layoutCoordinates.size
            },
        painter = painterResource(img),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    ShimmerEffect(
        modifier = modifier,
        density = density,
        size = size,
        rotationX = rotationX,
        rotationY = rotationY,
        shape = shape
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
    val transition = rememberInfiniteTransition()
    var size by remember { mutableStateOf(IntSize.Zero) }

    val startX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(LightGray, Transparent, LightGray),
            start = Offset(startX, 0f),
            end = Offset(startX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}