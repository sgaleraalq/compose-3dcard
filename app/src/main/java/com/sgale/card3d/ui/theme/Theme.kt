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

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier

//@Composable
//fun Card3DTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    colors: Card3DColors = if (darkTheme) {
//        Card3DColors.defaultDarkColors()
//    } else {
//        Card3DColors.defaultLightColors()
//    },
//    background: Card3DBackground = Card3DBackground.defaultBackground(darkTheme),
//    content: @Composable () -> Unit
//) {
//    CompositionLocalProvider(
//        LocalCard3DColors provides colors,
//        LocalCard3DBackground provides background
//    ) {
//        Box(
//            modifier = Modifier.background(background.color)
//        ) {
//            content()
//        }
//    }
//}
//
//object Card3DTheme {
//
//    val colors: Card3DColors
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalColors.current
//
//    val background: DragonDexBackground
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalBackgroundTheme.current
//
//}