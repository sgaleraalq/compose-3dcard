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

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

public class FlipController {

    private var _size: IntSize = IntSize.Zero
    public fun updateSize(size: IntSize) { _size = size }

    public var rotationX:   MutableState<Float>     = mutableFloatStateOf(0f)
    public var rotationY:   MutableState<Float>     = mutableFloatStateOf(0f)
    public var isFlipped:   Boolean = false
    public var isFlipping:  Boolean = false

    private var totalDragX: Float = 0f
    private var hasFlipped: Boolean = false
    private var flip:       Boolean = false

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    public fun updateRotation(dragX: Float, dragY: Float) {
        totalDragX += dragX
        rotationX.value = (rotationX.value - dragY * 0.1f).coerceIn(-10f, 10f)
        rotationY.value = (rotationY.value + dragX * 0.1f).coerceIn(-10f, 10f)
    }

    public fun resetDragging() {
        totalDragX = 0f
    }

    public fun endDragging() {
        if (abs(totalDragX) >= _size.width / 2) {
            // Launch flip card function
            coroutineScope.launch {
                flip = true
                flipCard()
            }
        }
    }

    private suspend fun flipCard() {
        val duration = 500L
        val halfDuration = duration / 2
        var startRotationY = rotationY.value
        val targetRotationY = if (totalDragX > 0) 90f else -90f

        if (flip) {
            isFlipping = true
            val startTime = System.nanoTime()
            var currentTime: Long
            var progress: Float

            while (true) {
                currentTime = System.nanoTime() - startTime
                progress = (currentTime / 1_000_000f) / halfDuration

                if (progress >= 1f) {
                    break
                }

                rotationY.value = startRotationY + progress * (targetRotationY - startRotationY)
                delay(16L)
            }

            hasFlipped = true
            isFlipped = !isFlipped

            startRotationY = if (totalDragX > 0) -90f else 90f
            while (true) {
                currentTime = System.nanoTime() - startTime
                progress = ((currentTime / 1_000_000f) - halfDuration) / halfDuration

                if (progress >= 1f) {
                    break
                }

                rotationY.value = startRotationY+ progress * (0f - startRotationY)
                delay(16L)
            }

            rotationX.value = 0f
            flip = false
            hasFlipped = false
            isFlipping = false
        }
    }
}