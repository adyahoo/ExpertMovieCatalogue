package id.ac.mymoviecatalogue.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.mymoviecatalogue.core.data.source.local.entity.MoviesEntity

@Database(entities = [MoviesEntity::class], version = 1, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}