package com.example.moviedb.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.moviedb.MovieDBApplication
import com.example.moviedb.database.MoviesRepository
import com.example.moviedb.database.SavedMovieRepository
import com.example.moviedb.model.Movie
import com.example.moviedb.model.MovieDetails
import com.example.moviedb.model.MovieReviews
import com.example.moviedb.model.MovieVideos
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MovieListUiState {
    data class Success(val movies: List<Movie>) : MovieListUiState
    object Error : MovieListUiState
    object Loading : MovieListUiState
}

sealed interface SelectedMovieUiState {
    data class Success(
        val movie: Movie,
        val movieDetails: MovieDetails,
        val movieReviews: MovieReviews,
        val movieVideos: MovieVideos
    ) : SelectedMovieUiState
    object Error : SelectedMovieUiState
    object Loading : SelectedMovieUiState
}

class MovieDBViewModel(private val moviesRepository: MoviesRepository,
                       private val localMoviesRepository: SavedMovieRepository,
    ) : ViewModel() {

    var movieListUiState: MovieListUiState by mutableStateOf(MovieListUiState.Loading)
        private set

    var selectedMovieUiState: SelectedMovieUiState by mutableStateOf(SelectedMovieUiState.Loading)
        private set

    init {
        getPopularMovies()
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                val fetchedMovies = moviesRepository.getTopRatedMovies().results
                cacheMovies(fetchedMovies)
                MovieListUiState.Success(fetchedMovies)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getPopularMovies() {

//        private val workManager = WorkManager.getInstance()

        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                val fetchedMovies = moviesRepository.getPopularMovies().results
                cacheMovies(fetchedMovies)
                MovieListUiState.Success(fetchedMovies)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getPopularWorkerMovies() {
//        private val workManager = WorkManager.getInstance()

        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(moviesRepository.getPopularMovies().results)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
//            cacheMovie(moviesRepository.getPopularMovies().results)
//            WorkManagerMoviesRepository.cacheMovies()
        }
    }

    private fun getSavedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(localMoviesRepository.getMovies())
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getCachedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(localMoviesRepository.getMovies())
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(localMoviesRepository.getFavoriteMovies())
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun favoriteMovie(movie: Movie, details: MovieDetails, reviews: MovieReviews, videos: MovieVideos) {
        movie.favorite = true
        viewModelScope.launch {
            localMoviesRepository.insertMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, MovieDetails(), MovieReviews(), MovieVideos())
        }
    }

    fun unfavoriteMovie(movie: Movie, details: MovieDetails, reviews: MovieReviews, videos: MovieVideos) {
        movie.favorite = false
        viewModelScope.launch {
            localMoviesRepository.unfavoriteMovie(movie.id)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, MovieDetails(), MovieReviews(), MovieVideos())
        }
    }

    fun cacheMovies(movieList: List<Movie>) {
        viewModelScope.launch {
            localMoviesRepository.decacheMovies()
            // for each movie save to database
            for (movie in movieList) {
                movie.cached = true
                localMoviesRepository.insertMovie(movie)
//                selectedMovieUiState = SelectedMovieUiState.Success(movie,
//                    MovieDetails(),
//                    MovieReviews(),
//                    MovieVideos())
            }
        }
    }

    fun setSelectedMovie(movie: Movie) {
        viewModelScope.launch {
            selectedMovieUiState = SelectedMovieUiState.Loading
            selectedMovieUiState = try {
                val movieDetails = moviesRepository.getMovieDetails(movie.id)
                val movieReviews = moviesRepository.getMovieReviews(movie.id)
                val movieVideos = moviesRepository.getMovieVideos(movie.id)
                SelectedMovieUiState.Success(movie, MovieDetails(), MovieReviews(), MovieVideos())
            } catch (e: IOException) {
                SelectedMovieUiState.Error
            } catch (e: HttpException) {
                SelectedMovieUiState.Error
            }
        }
    }

    fun setSelectedMovieCategory(option: String) {
        when (option) {
            "popular" -> {
                getPopularMovies()
                // TODO: add caching
            }
            "top_rated" -> {
                getTopRatedMovies()
            }
            "favorite" -> {
                getFavoriteMovies()
            }
            "cached" -> {
                getCachedMovies()
            }
            else -> {
                getPopularMovies()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieDBApplication)
                val moviesRepository = application.container.moviesRepository
                val localMoviesRepository = application.container.localMoviesRepository
                MovieDBViewModel(moviesRepository = moviesRepository,
                    localMoviesRepository = localMoviesRepository,
                )
            }
        }
    }
}