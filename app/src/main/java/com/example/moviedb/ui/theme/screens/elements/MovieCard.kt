package com.example.moviedb.ui.theme.screens.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviedb.model.Movie
import com.example.moviedb.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    movie: Movie,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = {
            onMovieClicked(movie)
        }
    ) {
        Row(modifier = Modifier.clickable { onMovieClicked(movie) }
            .height(100.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight().weight(0.5f)//weight(1f, fill = true)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Ensure the Box takes up all available space
                        .clip(shape = MaterialTheme.shapes.medium)
                ) {
                    AsyncImage(
                        model = Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_WIDTH + movie.posterPath,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop, // Adjust content scale
                        modifier = Modifier.fillMaxSize() // Ensure the image fills the Box
                    )
                }
            }
            //Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_WIDTH + movie.posterPath
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1.5f)
            ) {
                Text(
                    text = movie.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = movie.releaseDate,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
