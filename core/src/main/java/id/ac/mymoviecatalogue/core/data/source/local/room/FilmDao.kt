package id.ac.mymoviecatalogue.core.data.source.local.room

import androidx.room.*
import id.ac.mymoviecatalogue.core.data.source.local.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM tb_movie")
    fun getAllMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM tb_movie where isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM tb_movie where movieId = :movieId")
    fun getMovieById(movieId: Int): Flow<MoviesEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movies: List<MoviesEntity>)

    @Update
    suspend fun updateMovie(movie: MoviesEntity)
}