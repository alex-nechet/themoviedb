package com.alex.themoviedb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alex.themoviedb.BuildConfig
import com.alex.themoviedb.theme.TheMovieDbTheme
import com.alex.themoviedb.ui.list.MovieListScreen
import com.alex.themoviedb.ui.list.MovieListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheMovieDbTheme {
                MovieListScreen.Content(
                    viewModel = koinViewModel<MovieListViewModel>()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheMovieDbTheme {
//        MovieListScreen.Content()
    }
}