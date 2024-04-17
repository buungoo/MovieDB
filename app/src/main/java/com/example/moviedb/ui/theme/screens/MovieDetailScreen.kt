package com.example.moviedb.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviedb.model.Movie
import com.example.moviedb.utils.Constants

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column {

        Box() {
            AsyncImage(
                model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + movie.backdropPath,
                contentDescription = movie.title,
                modifier = modifier
                    .fillMaxWidth()
                    .size(300.dp),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = movie.releaseDate,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.size(8.dp))

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            items(movie.categories) { category ->
                Card(modifier = Modifier
                    .height(40.dp)
                    .padding(8.dp)
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            ExternalInformationButton(
                text = "IMDB",
                url = Constants.IMDB_BASE_URL + movie.imdbId,
            )

            Spacer(modifier = Modifier.size(8.dp))

            movie.homePageUrl?.let {
                ExternalInformationButton(
                    text = "Home page",
                    url = it,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ExternalInformationButton(
    text: String,
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        },
        modifier = modifier
    ) {
        Text(text = text)
    }
}