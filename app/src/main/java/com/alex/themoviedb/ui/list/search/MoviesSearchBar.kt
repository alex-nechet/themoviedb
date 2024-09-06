package com.alex.themoviedb.ui.list.search

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alex.themoviedb.R
import com.alex.themoviedb.ui.list.ListScreenAction
import com.alex.themoviedb.ui.list.MovieList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieList.MoviesSearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onAction: (ListScreenAction) -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    SearchBar(
        modifier = modifier.fillMaxSize(),
        query = query,
        active = true,
        placeholder = { Text(text = stringResource(R.string.search)) },
        onSearch = {},
        onQueryChange = { onAction(ListScreenAction.Search(it)) },
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onAction(ListScreenAction.Search("")) },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                )
            }
        },
        content = content
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieSearchBarPreview(){
    MovieList.MoviesSearchBar(
        query = "Wolverine",
        onAction = {},
        content = {}
    )
}