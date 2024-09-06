package com.alex.themoviedb.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.alex.themoviedb.R
import com.alex.themoviedb.theme.Dimens

@Composable
fun ErrorContent(
    errorMessage: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(Dimens.SPACE_16.dp),
        verticalArrangement = Arrangement.spacedBy(Dimens.SPACE_8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Button(onClick = onRetryClick) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorContentPreview() {
    ErrorContent(
        errorMessage = "Something went wrong",
        onRetryClick = {}
    )
}