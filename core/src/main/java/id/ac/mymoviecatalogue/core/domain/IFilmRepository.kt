package id.ac.mymoviecatalogue.core.domain

import id.ac.mymoviecatalogue.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IFilmRepository {
    fun getListMovies(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)
}