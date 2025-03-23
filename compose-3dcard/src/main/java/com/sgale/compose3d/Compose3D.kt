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

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Immutable
public data class ShimmeringColors(
    public val shimmering: Boolean = false
)

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
}