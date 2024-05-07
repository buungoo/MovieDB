package com.example.moviedb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.moviedb.model.Movie

@Dao
interface MovieDao {

    @Transaction
    suspend fun insertMovie(movie: Movie) {
        val localMovie = getMovie(movie.id)
        if (localMovie != null) {
            movie.favorite = movie.favorite || localMovie.favorite
            movie.cached = movie.cached || localMovie.cached
        }
        insertMovieInternal(movie)
    }

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovie(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieInternal(movie: Movie)

    @Transaction
    suspend fun decacheMovies() {
        updateCachedStatusForFavorites()
        deleteNonFavoriteMovies()
    }

    @Query("DELETE FROM movies WHERE favorite = 0")
    suspend fun deleteNonFavoriteMovies()

    @Query("UPDATE movies SET cached = 0 WHERE favorite = 1")
    suspend fun updateCachedStatusForFavorites()

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: Long): Movie

    @Query("SELECT * FROM movies WHERE cached = 1")
    suspend fun getCachedMovies(): List<Movie>

    @Transaction
    suspend fun unfavoriteMovie(id: Long) {
        val localMovie = getMovie(id)
        if (localMovie.cached) {
            localMovie.favorite = false
            insertMovieInternal(localMovie)
        } else {
            deleteMovie(id)
        }
    }

    @Query("SELECT * FROM movies WHERE favorite = 1")
    suspend fun getFavoriteMovies(): List<Movie>
}