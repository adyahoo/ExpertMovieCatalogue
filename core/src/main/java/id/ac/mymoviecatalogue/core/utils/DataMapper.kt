package id.ac.mymoviecatalogue.core.utils

import id.ac.mymoviecatalogue.core.data.source.local.entity.MoviesEntity
import id.ac.mymoviecatalogue.core.data.source.remote.response.MovieDetailResponse
import id.ac.mymoviecatalogue.core.data.source.remote.response.ResultsItemMovie
import id.ac.mymoviecatalogue.core.domain.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<ResultsItemMovie>): List<MoviesEntity> {
        val movieList = ArrayList<MoviesEntity>()
        input.map {
            val movie = MoviesEntity(
                it.id,
                it.title,
                it.releaseDate,
                it.overview,
                null,
                null,
                it.posterPath,
                false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponseToEntity(input: MovieDetailResponse): MoviesEntity {
        return MoviesEntity(
            input.id,
            input.title,
            input.releaseDate,
            input.overview,
            input.genres.joinToString { it.name },
            input.productionCompanies.joinToString { it.name },
            input.posterPath,
            false
        )
    }

    fun mapEntitiesToDomain(input: List<MoviesEntity>): List<Movie> {
        return input.map {
            Movie(
                it.movieId,
                it.title,
                it.releaseDate,
                it.overview,
                it.genre,
                it.production,
                it.poster,
                it.isFavorite
            )
        }
    }

    fun mapEntityToDomain(input: MoviesEntity): Movie {
        return Movie(
            input.movieId,
            input.title,
            input.releaseDate,
            input.overview,
            input.genre,
            input.production,
            input.poster,
            input.isFavorite
        )
    }

    fun mapDomainToEntity(input: Movie): MoviesEntity {
        return MoviesEntity(
            input.movieId,
            input.title,
            input.releaseDate,
            input.overview,
            input.genre,
            input.production,
            input.poster,
            input.isFavorite
        )
    }
}