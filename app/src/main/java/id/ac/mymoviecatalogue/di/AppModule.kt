package id.ac.mymoviecatalogue.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.mymoviecatalogue.core.domain.FilmInteractor
import id.ac.mymoviecatalogue.core.domain.FilmUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideFilmUseCase(filmInteractor: FilmInteractor): FilmUseCase
}