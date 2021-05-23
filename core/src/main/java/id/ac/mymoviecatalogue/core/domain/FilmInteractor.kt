package id.ac.mymoviecatalogue.core.domain

import id.ac.mymoviecatalogue.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmInteractor @Inject constructor(private val filmRepository: IFilmRepository) : FilmUseCase {
    override fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return filmRepository.getListMovies()
    }

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> {
        return filmRepository.getDetailMovie(movieId)
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return filmRepository.getFavoriteMovies()
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        return filmRepository.setFavoriteMovie(movie, state)
    }

}