package com.alex.themoviedb.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.PagingSource
import com.alex.data.remote.datasource.MovieDbRemoteDataSourceImpl
import com.alex.data.remote.dto.MovieDto
import com.alex.themoviedb.BuildConfig
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

object MovieListScreen {

    @Composable
    fun Content() {
        val remote = koinInject<PagingSource<Int, MovieDto>>()

    }
}