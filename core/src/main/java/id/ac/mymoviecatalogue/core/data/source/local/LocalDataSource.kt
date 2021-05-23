package id.ac.mymoviecatalogue.core.data.source.local

import id.ac.mymoviecatalogue.core.data.source.local.entity.MoviesEntity
import id.ac.mymoviecatalogue.core.data.source.local.room.FilmDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val filmDao: FilmDao){
    fun getAllMovies() = filmDao.getAllMovies()

    fun getFavoriteMovies() = filmDao.getFavoriteMovies()

    fun getMovieById(movieId: Int) = filmDao.getMovieById(movieId)

    suspend fun insertMovie(movies: List<MoviesEntity>) = filmDao.insertMovie(movies)

    suspend fun updateMovie(movie: MoviesEntity) = filmDao.updateMovie(movie)

    fun setFavoriteMovie(movie: MoviesEntity, newState: Boolean) {
        movie.isFavorite = newState
        GlobalScope.launch(Dispatchers.IO) {
            filmDao.updateMovie(movie)
        }
    }
}