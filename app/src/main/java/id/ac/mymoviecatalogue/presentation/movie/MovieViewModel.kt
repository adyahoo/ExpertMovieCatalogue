package id.ac.mymoviecatalogue.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.ac.mymoviecatalogue.core.domain.FilmUseCase
import id.ac.mymoviecatalogue.core.domain.Movie
import id.ac.mymoviecatalogue.core.vo.Resource
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val filmUseCase: FilmUseCase) : ViewModel() {
    fun getMovies(): LiveData<Resource<List<Movie>>> = filmUseCase.getAllMovies().asLiveData()
}