package id.ac.mymoviecatalogue.core.domain

import id.ac.mymoviecatalogue.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface FilmUseCase {
    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}