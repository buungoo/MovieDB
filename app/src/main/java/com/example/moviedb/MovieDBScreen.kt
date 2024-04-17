package com.example.moviedb

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviedb.screens.MovieDetailsScreen
import com.example.moviedb.screens.MovieListScreen
import com.example.moviedb.ui.theme.screens.StartScreen
import com.example.moviedb.viewmodels.MovieDBViewModel
import com.example.themoviedb.database.Movies

enum class MovieDBScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    List(title = R.string.movie_list),
    Detail(title = R.string.movie_detail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDBAppBar(
    currentScreen: MovieDBScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun MovieDBApp(
    viewModel: MovieDBViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MovieDBScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieDBScreen.Start.name
    )

    Scaffold(
        topBar = {
            MovieDBAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = MovieDBScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            composable(route = MovieDBScreen.Start.name) {
                 StartScreen(
                     onOptionClicked = { navController.navigate(MovieDBScreen.List.name) },
                     modifier = Modifier
                         .fillMaxSize()
                         .padding(16.dp)
                 )
            }

            composable(route = MovieDBScreen.List.name) {
                MovieListScreen(
                    movieList = Movies().getMovies(),
                    onMovieClicked = { movie ->
                        viewModel.setSelectedMovie(movie)
                        navController.navigate(MovieDBScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = MovieDBScreen.Detail.name) {
                uiState.selectedMovie?.let { movie ->
                    MovieDetailsScreen(
                        movie = movie,
                        modifier = Modifier
                    )
                }
            }
        }

    }
}