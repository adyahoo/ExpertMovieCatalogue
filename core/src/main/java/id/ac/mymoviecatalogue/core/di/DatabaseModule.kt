package id.ac.mymoviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.ac.mymoviecatalogue.core.data.source.local.room.FilmDao
import id.ac.mymoviecatalogue.core.data.source.local.room.FilmDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FilmDatabase {
        val passPhrase = SQLiteDatabase.getBytes("ExpertMovieCatalogue".toCharArray())
        val factory = SupportFactory(passPhrase)
        return Room.databaseBuilder(
            context,
            FilmDatabase::class.java,
            "db_film"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideFilmDao(database: FilmDatabase): FilmDao {
        return database.filmDao()
    }
}