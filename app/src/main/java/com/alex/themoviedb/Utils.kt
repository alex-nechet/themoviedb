package com.alex.themoviedb


enum class ImageSize {
    W500, ORIGINAL
}

infix fun String.buildImageWithSize(imageSize: ImageSize) = when {
    this.isEmpty() -> ""
    imageSize == ImageSize.W500 -> BuildConfig.IMAGE_URL_W500 + this
    imageSize == ImageSize.ORIGINAL -> BuildConfig.IMAGE_URL_ORIGINAL + this
    else -> ""
}