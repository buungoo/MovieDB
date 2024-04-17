package com.example.moviedb.viewmodels

import androidx.lifecycle.ViewModel
import com.example.moviedb.database.MovieDBUiState
import com.example.moviedb.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieDBViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MovieDBUiState())
    val uiState: StateFlow<MovieDBUiState> = _uiState.asStateFlow()

    fun setSelectedMovie(movie: Movie) {
        _uiState.update {
            it.copy(selectedMovie = movie)
        }
    }
}