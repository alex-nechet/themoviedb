package com.alex.themoviedb.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.alex.themoviedb.ImageSize
import com.alex.themoviedb.buildImageWithSize
import com.alex.themoviedb.theme.Dimens

@Composable
fun PosterAndOverview(
    posterPath: String,
    overview: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
) {
    if (posterPath.isNotEmpty() || overview.isNotEmpty()) {
        Row(modifier = modifier) {
            AsyncImage(
                modifier = Modifier.height(Dimens.CARD_HEIGHT.dp),
                model = posterPath buildImageWithSize ImageSize.W500,
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.SPACE_8.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = overview,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PosterAndOverviewPreview() {
    PosterAndOverview(
        posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
        overview = "Short description of the movie"
    )
}