package com.example.moviedb.database

import com.example.moviedb.model.Movie
import com.example.moviedb.model.MovieDetails
import com.example.moviedb.model.MovieResponse
import com.example.moviedb.model.MovieReviews
import com.example.moviedb.model.MovieVideos
import com.example.moviedb.network.MovieDBApiService


interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse

    suspend fun getTopRatedMovies(): MovieResponse

    suspend fun getMovieDetails(movieId: Long): MovieDetails

    suspend fun getMovieReviews(movieId: Long): MovieReviews

    suspend fun getMovieVideos(movieId: Long): MovieVideos
}

class NetworkMoviesRepository(private val apiService: MovieDBApiService) : MoviesRepository {
    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MovieResponse {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return apiService.getMovieDetails(movieId)
    }

    override suspend fun getMovieReviews(movieId: Long): MovieReviews {
        return apiService.getMovieReviews(movieId)
    }

    override suspend fun getMovieVideos(movieId: Long): MovieVideos {
        return apiService.getMovieVideos(movieId)
    }
}

interface SavedMovieRepository {

    suspend fun insertMovie(movie: Movie)

    suspend fun getMovie(id: Long): Movie

    suspend fun getMovies(): List<Movie>

    suspend fun favoriteMovie(movie: Movie)

    suspend fun unfavoriteMovie(id: Long)

    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun decacheMovies()
}

class LocalMovieRepository(private val movieDao: MovieDao): SavedMovieRepository {
    override suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    override suspend fun getMovie(id: Long): Movie {
        return movieDao.getMovie(id)
    }

    override suspend fun getMovies(): List<Movie> {
        return movieDao.getCachedMovies()
    }

    override suspend fun favoriteMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    override suspend fun unfavoriteMovie(id: Long) {
        movieDao.unfavoriteMovie(id)
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return movieDao.getFavoriteMovies()
    }

    override suspend fun decacheMovies() {
        movieDao.decacheMovies()
    }
}

//class FavoriteMoviesRepository(private val movieDao: MovieDao): SavedMovieRepository {
//    override suspend fun getSavedMovies(): List<Movie> {
//        return movieDao.getFavoriteMovies()
//    }
//
//    override suspend fun insertMovie(id: Long) {
//        movieDao.favoriteMovie(id)
//    }
//
//    override suspend fun getMovie(id: Long): Movie {
//        return movieDao.getMovie(id)
//    }
//
//    override suspend fun deleteMovie(movie: Movie) {
//        movieDao.unfavoriteMovie(movie.id)
//    }
//}
//
//class CacheMovieRepository(private val movieDao: MovieDao): SavedMovieRepository {
//    override suspend fun getSavedMovies(): List<Movie> {
//        return movieDao.getCacheMovies()
//    }
//
//    override suspend fun insertMovie(movie: Movie) {
//        movieDao.insertCacheMovie(movie)
//    }
//
//    override suspend fun getMovie(id: Long): Movie {
//        return movieDao.getMovie(id)
//    }
//
//    override suspend fun deleteMovie(movie: Movie) {
//        movieDao.unfavoriteMovie(movie.id)
//    }
//}