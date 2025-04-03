package com.sgale.compose3d

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize

public object LayoutUtils {
    public fun calculateImageSize(containerSize: IntSize, imageSize: IntSize, scale: ContentScale): IntSize {
        val containerWidth = containerSize.width
        val containerHeight = containerSize.height
        val imageWidth = imageSize.width
        val imageHeight = imageSize.height

        if (imageWidth <= 0 || imageHeight <= 0) return containerSize

        val aspectRatio = imageWidth.toFloat() / imageHeight.toFloat()
        val containerAspectRatio = containerWidth.toFloat() / containerHeight.toFloat()

        return when (scale) {
            ContentScale.Fit -> {
                if (containerAspectRatio > aspectRatio) {
                    IntSize((containerHeight * aspectRatio).toInt(), containerHeight)
                } else {
                    IntSize(containerWidth, (containerWidth / aspectRatio).toInt())
                }
            }
            ContentScale.Crop -> {
                if (containerAspectRatio > aspectRatio) {
                    IntSize(containerWidth, (containerWidth / aspectRatio).toInt())
                } else {
                    IntSize((containerHeight * aspectRatio).toInt(), containerHeight)
                }
            }
            ContentScale.FillBounds -> {
                IntSize(containerWidth, containerHeight)
            }
            ContentScale.FillHeight -> {
                IntSize((containerHeight * aspectRatio).toInt(), containerHeight)
            }
            ContentScale.FillWidth -> {
                IntSize(containerWidth, (containerWidth / aspectRatio).toInt())
            }
            ContentScale.Inside -> {
                if (imageHeight <= containerWidth && imageHeight <= containerHeight) {
                    IntSize(imageWidth, imageHeight)
                } else {
                    if (containerAspectRatio > aspectRatio) {
                        IntSize((containerHeight * aspectRatio).toInt(), containerHeight)
                    } else {
                        IntSize(containerWidth, (containerWidth / aspectRatio).toInt())
                    }
                }
            }
            else -> containerSize // Default case
        }
    }
}