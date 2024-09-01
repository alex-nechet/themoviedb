package com.alex.themoviedb.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.alex.domain.movies.entity.Movie
import com.alex.themoviedb.ImageSize
import com.alex.themoviedb.buildImageWithSize

object MovieListScreen {

    @Composable
    fun Content(viewModel: MovieListViewModel) {
        val data = viewModel.movies.collectAsLazyPagingItems()

        LazyColumn(
            state = rememberLazyListState()
        ) {
            item {
                PagingStateItem(data.loadState.refresh) {
                    Text("Movies")
                }
            }

            items(
                count = data.itemCount,
                key = { it },
            ) { index ->
                val item = data[index]
                PagingItem(item = item)
            }

            item { PagingStateItem(data.loadState.append) }
        }
    }

    @Composable
    fun PagingItem(item: Movie?) {
        item?.let {
            Text(text = it.title, style = MaterialTheme.typography.titleLarge)
            AsyncImage(
                model = it.posterPath buildImageWithSize ImageSize.W500,
                contentDescription = null
            )
        }
    }

    @Composable
    fun PagingStateItem(state: LoadState, notLoadingItem: @Composable () -> Unit = {}) {
        when (state) {
            is LoadState.NotLoading -> notLoadingItem()
            is LoadState.Loading -> CircularProgressIndicator()
            is LoadState.Error -> Text(text = state.error.message.orEmpty())
        }
    }
}