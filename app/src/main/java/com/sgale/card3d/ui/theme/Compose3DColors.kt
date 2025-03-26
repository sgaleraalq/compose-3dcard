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

package com.sgale.card3d.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.sgale.card3d.R

@Immutable
data class Compose3DColors(
    val background: Color,
    val primary: Color
) {
    companion object {
        @Composable
        fun defaultLightColors(): Compose3DColors {
            return Compose3DColors(
                background = colorResource(R.color.background_light),
                primary = colorResource(R.color.primary)
            )
        }

        @Composable
        fun defaultDarkColors(): Compose3DColors {
            return Compose3DColors(
                background = colorResource(R.color.background_dark),
                primary = colorResource(R.color.primary)
            )
        }
    }
}