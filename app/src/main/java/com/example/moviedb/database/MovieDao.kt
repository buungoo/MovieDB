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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieInternal(movie: Movie)

    @Transaction
    suspend fun decacheMovies() {
        updateCachedStatusForFavorites()
        deleteNonFavoriteMovies()
    }


    // TODO: unfavorite
    @Transaction
    suspend fun unfavoriteMovie(id: Long) {
        val movie = getMovie(id)
        if (movie.favorite) {
            unfavoriteMovieInternal(id)
        } else {
            deleteMovie(id)
        }
    }

    @Query("DELETE FROM movies WHERE favorite = 0")
    suspend fun deleteNonFavoriteMovies()

    @Query("UPDATE movies SET cached = 0 WHERE favorite = 1")
    suspend fun updateCachedStatusForFavorites()

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: Long): Movie

    @Query("SELECT * FROM movies WHERE cached = 1")
    suspend fun getCachedMovies(): List<Movie>

    @Query("UPDATE movies SET favorite = 1 WHERE id = :id")
    suspend fun favoriteMovie(id: Long)

    @Query("UPDATE movies SET favorite = 0 WHERE id = :id")
    suspend fun unfavoriteMovie(id: Long)

    @Query("SELECT * FROM movies WHERE favorite = 1")
    suspend fun getFavoriteMovies(): List<Movie>
}