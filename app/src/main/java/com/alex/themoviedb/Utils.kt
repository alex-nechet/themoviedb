package com.alex.themoviedb

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


enum class ImageSize {
    W500, ORIGINAL
}

infix fun String.buildImageWithSize(imageSize: ImageSize) = when {
    this.isEmpty() -> ""
    imageSize == ImageSize.W500 -> BuildConfig.IMAGE_URL_W500 + this
    imageSize == ImageSize.ORIGINAL -> BuildConfig.IMAGE_URL_ORIGINAL + this
    else -> ""
}


fun Long.convertMillisToDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = Date(this)
    return sdf.format(date)
}