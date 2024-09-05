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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.alex.domain.movies.entity.MovieSuggestions
import com.alex.themoviedb.ImageSize
import com.alex.themoviedb.R
import com.alex.themoviedb.buildImageWithSize
import com.alex.themoviedb.navigation.Route
import com.alex.themoviedb.ui.common.ErrorContent
import com.alex.themoviedb.ui.common.PosterAndOverview
import kotlinx.coroutines.flow.flowOf

object MovieList {

    @Composable
    fun Screen(
        viewModel: MovieListViewModel,
        modifier: Modifier = Modifier,
        navigate: (Route) -> Unit
    ) {
        val data = viewModel.movies.collectAsLazyPagingItems()
        val suggestions = viewModel.suggestions.collectAsLazyPagingItems()

        Content(
            data = data,
            suggestions = suggestions,
            modifier = modifier,
            onAction = { action ->
                when (action) {
                    is ListScreenAction.OpenDetails -> navigate(
                        Route.Details(action.movieId)
                    )

                    ListScreenAction.Retry -> data.retry()
                    is ListScreenAction.Search -> viewModel.query.value = action.query
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        data: LazyPagingItems<Movie>,
        suggestions: LazyPagingItems<MovieSuggestions>,
        modifier: Modifier = Modifier,
        onAction: (ListScreenAction) -> Unit,
    ) {
        LaunchedEffect(Unit) {
            onAction(ListScreenAction.Search(""))
        }

        val query = remember { mutableStateOf("") }

        Scaffold(
            topBar = {
                SearchBar(
                    modifier = Modifier.height(72.dp),
                    query = query.value,
                    active = true,
                    placeholder = { Text(text = stringResource(R.string.search)) },
                    onSearch = {},
                    onQueryChange = {
                        query.value = it
                        onAction(ListScreenAction.Search(it))
                    },
                    onActiveChange = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = Color.Transparent,
                    ),
                    content = {}
                )
            },
            content = { paddingValues ->
                LazyColumn(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    item {
                        PagingStateItem(
                            state = data.loadState.refresh,
                            content = { StickyHeader() },
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
                            onItemClick = {  onAction(ListScreenAction.OpenDetails(it)) }
                        )
                    }

                    item {
                        PagingStateItem(
                            state = data.loadState.append,
                            onRetryClick = { onAction(ListScreenAction.Retry) })
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(vertical = 72.dp)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surface),
                    state = rememberLazyListState(),
                ) {
                    items(
                        count = suggestions.itemCount,
                        key = { it },
                    ) { index ->
                        val item = suggestions[index]
                        item?.let {
                            SuggestionChip(
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(it.title) },
                                onClick = {
                                    onAction(ListScreenAction.OpenDetails(it.id))
                                }
                            )
                        }

                    }
                }
            }
        )
    }

    @Composable
    private fun StickyHeader() {
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                text = "Movies"
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
                            .height(256.dp)
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
    val suggestions = listOf(
        MovieSuggestions(0, "Deadpool"),
        MovieSuggestions(1, "Batman")
    )

    MovieList.Content(
        data = flowOf(PagingData.from(previewList)).collectAsLazyPagingItems(),
        suggestions = flowOf(PagingData.from(suggestions)).collectAsLazyPagingItems(),
        onAction = {})
}