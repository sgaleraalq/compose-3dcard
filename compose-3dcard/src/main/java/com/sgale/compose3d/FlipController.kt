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

public class FlipController {

    public var rotationX: MutableState<Float> = mutableFloatStateOf(0f)
    public var rotationY: MutableState<Float> = mutableFloatStateOf(0f)

    public fun updateRotation(dragX: Float, dragY: Float) {
        rotationX.value = (rotationX.value - dragY * 0.1f).coerceIn(-10f, 10f)
        rotationY.value = (rotationY.value + dragX * 0.1f).coerceIn(-10f, 10f)
    }
}