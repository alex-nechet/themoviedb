package com.alex.themoviedb.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.alex.domain.movies.entity.Movie
import com.alex.themoviedb.ImageSize
import com.alex.themoviedb.R
import com.alex.themoviedb.buildImageWithSize
import com.alex.themoviedb.navigation.Route
import com.alex.themoviedb.theme.Dimens
import com.alex.themoviedb.ui.common.ErrorContent
import com.alex.themoviedb.ui.common.PosterAndOverview
import com.alex.themoviedb.ui.list.search.AutocompleteContent
import com.alex.themoviedb.ui.list.search.MoviesSearchBar
import kotlinx.coroutines.flow.flowOf

object MovieList {

    @Composable
    fun Screen(
        viewModel: MovieListViewModel,
        modifier: Modifier = Modifier,
        navigate: (Route) -> Unit
    ) {
        val data = viewModel.movies.collectAsLazyPagingItems()
        val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
        val query = viewModel.queryState.collectAsState()
        val payload = if (query.value.isEmpty()) data else searchResults
        val showSuggestions = remember { mutableStateOf(false) }

        Content(
            modifier = modifier,
            payload = payload,
            query = query.value,
            showSuggestions = showSuggestions.value,
            onAction = { action ->
                when (action) {
                    is ListScreenAction.OpenDetails -> navigate(
                        Route.Details(action.movieId)
                    )

                    ListScreenAction.Retry -> data.retry()
                    is ListScreenAction.Search -> {
                        viewModel.setQuery(action.query)
                        showSuggestions.value = true
                    }

                    ListScreenAction.CloseSuggestions -> {
                        showSuggestions.value = false
                    }
                }
            }
        )
    }

    @Composable
    fun Content(
        showSuggestions: Boolean,
        payload: LazyPagingItems<Movie>,
        query: String,
        modifier: Modifier = Modifier,
        onAction: (ListScreenAction) -> Unit,
    ) {
        val isSuggestionsVisible = showSuggestions &&
                query.isNotEmpty() &&
                payload.itemCount > 1

        Box(modifier = modifier) {
            MoviesSearchBar(
                query = query,
                onAction = onAction,
                content = {
                    if (isSuggestionsVisible) {
                        AutocompleteContent(
                            searchResults = payload,
                            onItemClick = {
                                onAction(ListScreenAction.Search(it))
                                onAction(ListScreenAction.CloseSuggestions)
                            },
                            onCloseClick = { onAction(ListScreenAction.CloseSuggestions) }
                        )
                    }

                    MainContent(
                        data = payload,
                        onAction = onAction
                    )
                }
            )
        }
    }
}

@Composable
private fun MainContent(
    data: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onAction: (ListScreenAction) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(Dimens.SPACE_8.dp)
    ) {

        item {
            PagingStateItem(
                state = data.loadState.refresh,
                content = {
                    StickyHeader(
                        when {
                            data.itemCount > 0 -> stringResource(R.string.movies)
                            else -> stringResource(R.string.no_results)
                        }
                    )
                },
                onRetryClick = { onAction(ListScreenAction.Retry) }
            )
        }

        items(
            count = data.itemCount,
            key = { it },
        ) { index ->
            val item = data[index]
            PagingItem(
                item = item,
                onItemClick = { onAction(ListScreenAction.OpenDetails(it)) }
            )
        }

        item {
            PagingStateItem(
                state = data.loadState.append,
                onRetryClick = { onAction(ListScreenAction.Retry) })
        }
    }
}

@Composable
private fun StickyHeader(title: String, modifier: Modifier = Modifier) {
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

@Composable
private fun PagingItem(
    item: Movie?,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    item?.let {
        Card(
            modifier = modifier,
            onClick = { onItemClick(it.id) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column {
                    TitleBar(title = it.title, releaseDate = it.releaseDate)
                    PosterAndOverview(
                        posterPath = it.posterPath,
                        overview = it.overview,
                        maxLines = 6
                    )
                }

                AsyncImage(
                    modifier = Modifier
                        .height(Dimens.CARD_HEIGHT.dp)
                        .align(Alignment.BottomCenter),
                    alpha = 0.2f,
                    model = it.backdropPath buildImageWithSize ImageSize.W500,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun TitleBar(
    title: String,
    releaseDate: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White.copy(alpha = 0.5f))
            .padding(8.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            modifier = Modifier.weight(3f),
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            text = releaseDate,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun PagingStateItem(
    state: LoadState,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is LoadState.NotLoading -> content()
            is LoadState.Loading -> CircularProgressIndicator()
            is LoadState.Error -> ErrorContent(
                errorMessage = state.error.localizedMessage.orEmpty(),
                onRetryClick = onRetryClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentPreview() {
    val previewItem = Movie(
        backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
        id = 533535,
        overview = "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",

        posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
        releaseDate = "2024-07-24",
        title = "Deadpool & Wolverine",
    )

    val previewList = listOf(previewItem, previewItem, previewItem)
    MovieList.Content(
        payload = flowOf(PagingData.from(previewList)).collectAsLazyPagingItems(),
        query = previewItem.title,
        showSuggestions = true,
        onAction = {})
}