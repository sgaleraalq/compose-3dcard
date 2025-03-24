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

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
public fun Compose3DCard(
    modifier: Modifier = Modifier,
    img: Int,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp)
) {
    val density = LocalDensity.current.density
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
                cameraDistance = 12f * density
            )
            .shadow(
                elevation = 16.dp,
                shape = shape
            )
            .clip(shape),
        painter = painterResource(img),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    ShimmerEffect(
        offset = Offset(0f, 0f),
        size = IntSize(100, 100),
        rotationX = rotationX,
        rotationY = rotationY
    )
}

@Composable
public fun ShimmerEffect(
    offset: Offset,
    size: IntSize,
    rotationX: Float,
    rotationY: Float
){
    Box(
        modifier = Modifier
            .offset(offset.x.dp, offset.y.dp)
            .height(size.height.dp)
            .size(size.width.dp)
            .graphicsLayer(
                rotationX = rotationX,
                rotationY = rotationY,
                transformOrigin = TransformOrigin.Center
            )
            .background(Color.Red)
            .shimmerEffect()
    )
}

public fun Modifier.shimmerEffect() : Modifier = composed {
    val transition  = rememberInfiniteTransition()
    var size        by remember { mutableStateOf(IntSize.Zero) }

    val startX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )


    background(
        brush = Brush.linearGradient(
            colors = listOf(Color.Gray, Color.LightGray),
            start = Offset(startX, 0f),
            end = Offset(startX + size.width.toFloat(), size.height.toFloat())
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(Color.LightGray, Color.Gray),
            start = Offset(startX, 0f),
            end = Offset(startX + size.width.toFloat(), size.height.toFloat())
        )
    )
    onGloballyPositioned {
        size = it.size
    }

}