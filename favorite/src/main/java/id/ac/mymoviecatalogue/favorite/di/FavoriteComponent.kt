package id.ac.mymoviecatalogue.favorite.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.ac.mymoviecatalogue.di.FavoriteModuleDependencies
import id.ac.mymoviecatalogue.favorite.FavoriteActivity

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}