package id.ac.mymoviecatalogue.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.ac.mymoviecatalogue.core.domain.FilmUseCase

class FavoriteViewModel(private val filmUseCase: FilmUseCase): ViewModel() {
    fun getFavoriteMovies() = filmUseCase.getFavoriteMovies().asLiveData()
}