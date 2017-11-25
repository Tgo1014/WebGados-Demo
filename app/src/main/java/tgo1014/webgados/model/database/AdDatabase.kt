package tgo1014.webgados.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import tgo1014.webgados.model.dao.AdDao
import tgo1014.webgados.model.objects.Ad
import tgo1014.webgados.utils.Converters

@Database(entities = arrayOf(Ad::class), version = 1)
@TypeConverters(Converters::class)
abstract class AdDatabase : RoomDatabase() {
    abstract fun adDao(): AdDao
}