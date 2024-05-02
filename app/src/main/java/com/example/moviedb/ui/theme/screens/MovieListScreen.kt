package com.example.moviedb.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviedb.model.Movie
import com.example.moviedb.ui.theme.screens.elements.MovieCard
import com.example.moviedb.viewmodels.MovieListUiState

@Composable
fun MovieListScreen(
    movieListUiState: MovieListUiState,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        when(movieListUiState) {
            is MovieListUiState.Success -> {
                items(movieListUiState.movies) { movie ->
                    MovieCard(
                        movie = movie,
                        onMovieClicked,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            is MovieListUiState.Loading -> {
                item {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            is MovieListUiState.Error -> {
                item {
                    Text(
                        text = "Error: Something went wrong!",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}