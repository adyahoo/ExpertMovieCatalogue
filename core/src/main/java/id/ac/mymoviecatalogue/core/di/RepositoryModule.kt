package id.ac.mymoviecatalogue.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.mymoviecatalogue.core.data.FilmRepository
import id.ac.mymoviecatalogue.core.domain.IFilmRepository

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(filmRepository: FilmRepository): IFilmRepository
}