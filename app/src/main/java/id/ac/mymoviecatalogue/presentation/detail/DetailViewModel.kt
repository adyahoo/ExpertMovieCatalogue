package id.ac.mymoviecatalogue.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.mymoviecatalogue.core.domain.FilmUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val filmUseCase: FilmUseCase) : ViewModel() {
    private val movieId = MutableLiveData<Int>()

    fun setSelectedMovie(movieId: Int) {
        this.movieId.value = movieId
    }

    val movieDetail = Transformations.switchMap(movieId) {
        filmUseCase.getDetailMovie(it).asLiveData()
    }

    fun setFavorite() {
        val movie = movieDetail.value

        if (movie != null) {
            val movieDetail = movie.data
            if (movieDetail != null) {
                val movieState = movieDetail.isFavorite
                val newState = !movieState
                filmUseCase.setFavoriteMovie(movieDetail, newState)
            }
        }
    }
}