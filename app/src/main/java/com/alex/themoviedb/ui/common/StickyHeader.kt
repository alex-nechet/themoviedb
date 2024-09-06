package com.alex.themoviedb.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alex.themoviedb.R
import com.alex.themoviedb.theme.Dimens

@Composable
fun StickyHeader(title: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.SPACE_16.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            text = title
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StickyHeaderPreview() {
    StickyHeader(stringResource(R.string.movies))
}