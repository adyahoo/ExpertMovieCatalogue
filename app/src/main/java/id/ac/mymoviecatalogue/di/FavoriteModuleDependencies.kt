package id.ac.mymoviecatalogue.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.mymoviecatalogue.core.domain.FilmUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun filmUseCase(): FilmUseCase
}