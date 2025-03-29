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

public class CardFlipper(
    private val duration: Long = 500L
) {
    public var startRotationY: Float = 0f
    public var targetRotationY: Float = 0f
    private var startTime: Long = 0L
    private var hasFlipped: Boolean = false

    public fun startFlip(totalDragX: Float): Pair<Float, Float> {
        startRotationY = 0f
        targetRotationY = if (totalDragX > 0) 90f else -90f
        startTime = System.nanoTime()
        return startRotationY to targetRotationY
    }

    public fun updateFlip(
        flip: Boolean,
        rotationY: Float,
        totalDragX: Float,
    ): Pair<Float, Boolean> {
        var updatedRotationY = rotationY
        var isFlipped = flip

        if (flip) {
            val halfDuration = duration / 2
            val currentTime = System.nanoTime() - startTime
            val progress = (currentTime / 1_000_000f) / halfDuration

            if (progress < 1f) {
                updatedRotationY = startRotationY + progress * (targetRotationY - startRotationY)
            } else {
                // Flip la carta
                if (!hasFlipped) {
                    hasFlipped = true
                    isFlipped = !isFlipped
                }

                val secondHalfProgress = ((currentTime / 1_000_000f) - halfDuration) / halfDuration
                updatedRotationY = if (secondHalfProgress < 1f) {
                    if (totalDragX > 0) {
                        -90f + secondHalfProgress * (0f + 90f)
                    } else {
                        90f - secondHalfProgress * (90f + 0f)
                    }
                } else {
                    0f
                }
            }
        }
        return updatedRotationY to isFlipped
    }
}
