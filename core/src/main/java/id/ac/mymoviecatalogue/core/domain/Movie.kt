package id.ac.mymoviecatalogue.core.domain

data class Movie(
    val movieId: Int,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val genre: String?,
    val production: String?,
    val poster: String,
    val isFavorite: Boolean
)