package com.alex.themoviedb.ui.list.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alex.domain.movies.entity.Movie
import com.alex.themoviedb.R
import com.alex.themoviedb.theme.Dimens
import com.alex.themoviedb.ui.list.MovieList
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun MovieList.AutocompleteContent(
    modifier: Modifier = Modifier,
    searchResults: LazyPagingItems<Movie>,
    onItemClick: (String) -> Unit,
    onCloseClick: () -> Unit
) {
    Card(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.SPACE_8.dp),
    ) {
        Column {
            LazyColumn(
                modifier =
                Modifier
                    .height(Dimens.CARD_HEIGHT.dp)
                    .fillMaxWidth()
                    .padding(Dimens.SPACE_8.dp),
                state = rememberLazyListState(),
                verticalArrangement = Arrangement.spacedBy(Dimens.SPACE_8.dp)
            ) {

                items(
                    count = searchResults.itemCount,
                    key = { it },
                ) { index ->
                    val item = searchResults[index]
                    item?.let {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onItemClick(it.title) },
                            text = it.title
                        )
                    }

                }
            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.SPACE_16.dp),
                onClick = onCloseClick,
                content = {
                    Text(stringResource(R.string.close_suggestions))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AutocompleteContentPreview() {
    val previewItem = Movie(
        backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
        id = 533535,
        overview = "A listless Wade Wilson",
        releaseDate = "2024-07-24",
        posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
        title = "Deadpool & Wolverine",
    )
    val previewList = listOf(previewItem, previewItem, previewItem)

    MovieList.AutocompleteContent(
        searchResults = flowOf(PagingData.from(previewList)).collectAsLazyPagingItems(),
        onItemClick = {},
        onCloseClick = {}
    )
}