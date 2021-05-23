package id.ac.mymoviecatalogue.core.data

import id.ac.mymoviecatalogue.core.data.source.local.LocalDataSource
import id.ac.mymoviecatalogue.core.data.source.remote.ApiResponse
import id.ac.mymoviecatalogue.core.data.source.remote.RemoteDataSource
import id.ac.mymoviecatalogue.core.data.source.remote.response.*
import id.ac.mymoviecatalogue.core.domain.IFilmRepository
import id.ac.mymoviecatalogue.core.domain.Movie
import id.ac.mymoviecatalogue.core.utils.AppExecutors
import id.ac.mymoviecatalogue.core.utils.DataMapper
import id.ac.mymoviecatalogue.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IFilmRepository {
    override fun getListMovies(): Flow<Resource<List<Movie>>> {
        return object : id.ac.mymoviecatalogue.core.data.NetworkBoundResource<List<Movie>, List<ResultsItemMovie>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItemMovie>>> {
                return remoteDataSource.getListMovies()
            }

            override suspend fun saveCallResult(data: List<ResultsItemMovie>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> {
        return object : id.ac.mymoviecatalogue.core.data.NetworkBoundResource<Movie, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieById(movieId).map { DataMapper.mapEntityToDomain(it) }
            }

            override fun shouldFetch(data: Movie?): Boolean {
                return data?.genre == null || data.production == null
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getDetailMovie(movieId)
            }

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                val dataEntity = DataMapper.mapResponseToEntity(data)
                localDataSource.updateMovie(dataEntity)
            }
        }.asFlow()
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val moviesEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(moviesEntity, state) }
    }
}