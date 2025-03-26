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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

private val LocalColors = compositionLocalOf<Compose3DColors> {
    error("No colors provided! Make sure to wrap all usages of Compose3DColors in Compose3DTheme.")
}

@Composable
fun Compose3DTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: Compose3DColors = if (darkTheme){
        Compose3DColors.defaultDarkColors()
    } else {
        Compose3DColors.defaultLightColors()
    },
    content: @Composable () -> Unit
){
    CompositionLocalProvider(
        LocalColors provides colors
    ) {
        Box {
            content()
        }
    }
}


object Compose3DTheme {

    val colors: Compose3DColors
        @Composable
        get() = LocalColors.current
}