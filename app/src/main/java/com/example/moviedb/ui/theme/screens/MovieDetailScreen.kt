package com.example.moviedb.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.moviedb.model.MovieVideo
import com.example.moviedb.ui.theme.getReviewCardScreenWidth
import com.example.moviedb.ui.theme.getScreenWidth
import com.example.moviedb.utils.Constants
import com.example.moviedb.viewmodels.MovieDBViewModel
import com.example.moviedb.viewmodels.SelectedMovieUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailScreen(
//    selectedMovieUiState: SelectedMovieUiState,
    movieDBViewModel: MovieDBViewModel,
    modifier: Modifier = Modifier
) {
    val selectedMovieUiState = movieDBViewModel.selectedMovieUiState
    when (selectedMovieUiState) {
        is SelectedMovieUiState.Success -> {

            LazyColumn {
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    ) {
                        AsyncImage(
                            model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + selectedMovieUiState.movieDetails.backdropPath,
                            contentDescription = selectedMovieUiState.movie.title,
                            modifier = modifier,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Row {
                        Text(
                            text = selectedMovieUiState.movie.title,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Switch(checked = selectedMovieUiState.movie.favorite, onCheckedChange = { isFavorite ->
                            if (isFavorite)
                                selectedMovieUiState.movie.favorite = false
                                movieDBViewModel.favoriteMovie(selectedMovieUiState.movie)
                            else
                                movieDBViewModel.unfavoriteMovie(selectedMovieUiState.movie.id)
                        })
                    }
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

                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(selectedMovieUiState.movieDetails.genres) { genre ->
                            Card(modifier.padding(horizontal = 4.dp)) {
                                Text(
                                    text = genre.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(4.dp)
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
                        selectedMovieUiState.movieDetails.imdbId?.let {
                            ExternalInformationButton(
                                text = "IMDB",
                                url = Constants.IMDB_BASE_URL + selectedMovieUiState.movieDetails.imdbId,
                            )
                        }

                        Spacer(modifier = Modifier.size(8.dp))

                        selectedMovieUiState.movieDetails.homepage?.let {
                            ExternalInformationButton(
                                text = "Home page",
                                url = it,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    val videoState = rememberLazyListState()
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        state = videoState,
                        flingBehavior = rememberSnapFlingBehavior(lazyListState = videoState)
                    ) {
                        items(selectedMovieUiState.movieVideos.results) { video ->
                            ExoPlayerView(video = video)
                        }
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    val reviewState = rememberLazyListState()
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        state = reviewState,
                        flingBehavior = rememberSnapFlingBehavior(lazyListState = reviewState)
                    ) {
                        items(selectedMovieUiState.movieReviews.results) { review ->
                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .width(getReviewCardScreenWidth())
                            ) {
                                Column {
                                    Text(text = review.author)
                                    Text(text = review.content)
                                }
                            }
                        }
                    }
                }
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

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerView(video: MovieVideo) {

    //TODO: not be the example video but one should build with the youtube base,
    //TODO: ytMediaSource should be used for each video
//    val ytMediaSource = remember(Constants.YOUTUBE_BASE_URL + video.key) {
//        MediaItem.fromUri(Constants.YOUTUBE_BASE_URL + video.key)
//    }

    // Get the current context
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context).build()

    // Create a MediaSource
    val mediaSource = remember(Constants.EXAMPLE_VIDEO_URI) {
        MediaItem.fromUri(Constants.EXAMPLE_VIDEO_URI)
    }

    // Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Use AndroidView to embed an Android View (PlayerView) into Compose
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .width(getScreenWidth())
            .height(180.dp) // Set your desired height
    )
}