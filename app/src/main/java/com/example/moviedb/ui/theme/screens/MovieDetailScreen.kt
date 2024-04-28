package com.example.moviedb.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviedb.utils.Constants
import com.example.moviedb.viewmodels.SelectedMovieUiState

@Composable
fun MovieDetailScreen(
    selectedMovieUiState: SelectedMovieUiState,
    modifier: Modifier = Modifier
) {
    when (selectedMovieUiState) {
        is SelectedMovieUiState.Success -> {
            Column(Modifier.width(IntrinsicSize.Max)) {
                Box(Modifier.fillMaxWidth().padding(0.dp)) {
                    AsyncImage(
                        model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + selectedMovieUiState.movie.backdropPath,
                        contentDescription = selectedMovieUiState.movie.title,
                        modifier = modifier,
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = selectedMovieUiState.movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedMovieUiState.movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedMovieUiState.movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
        is SelectedMovieUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        is SelectedMovieUiState.Error -> {
            Text(
                text = "Error...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}



//@Composable
//fun MovieDetailsScreen(
//    movie: Movie,
//    modifier: Modifier = Modifier
//) {
//    Column {
//
//        Box() {
//            AsyncImage(
//                model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + movie.backdropPath,
//                contentDescription = movie.title,
//                modifier = modifier
//                    .fillMaxWidth()
//                    .size(300.dp),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        Text(
//            text = movie.title,
//            style = MaterialTheme.typography.headlineSmall
//        )
//        Spacer(modifier = Modifier.size(8.dp))
//
//        Text(
//            text = movie.releaseDate,
//            style = MaterialTheme.typography.bodySmall
//        )
//        Spacer(modifier = Modifier.size(8.dp))
//
//        Text(
//            text = movie.overview,
//            style = MaterialTheme.typography.bodySmall,
//            maxLines = 3,
//            overflow = TextOverflow.Ellipsis,
//        )
//
//        Spacer(modifier = Modifier.size(8.dp))
//
//        LazyRow(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            items(movie.categories) { category ->
//                Card(modifier = Modifier
//                    .height(40.dp)
//                    .padding(8.dp)
//                ) {
//                    Text(
//                        text = category.toString(),
//                        style = MaterialTheme.typography.bodySmall,
//                        modifier = Modifier.fillMaxHeight()
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.size(8.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Spacer(modifier = Modifier.weight(1f))
//            ExternalInformationButton(
//                text = "IMDB",
//                url = Constants.IMDB_BASE_URL + movie.imdbId,
//            )
//
//            Spacer(modifier = Modifier.size(8.dp))
//
//            movie.homePageUrl?.let {
//                ExternalInformationButton(
//                    text = "Home page",
//                    url = it,
//                )
//            }
//            Spacer(modifier = Modifier.weight(1f))
//        }
//    }
//}
//
//@Composable
//fun ExternalInformationButton(
//    text: String,
//    url: String,
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//
//    Button(
//        onClick = {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            context.startActivity(intent)
//        },
//        modifier = modifier
//    ) {
//        Text(text = text)
//    }
//}